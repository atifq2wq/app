package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object RewardedAdManager {
    private const val TAG = "RewardedAdManager"

    // Google AdMob Test Rewarded Ad Unit ID during development
    private const val DEFAULT_TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

    // Configurable Ad Unit ID (can be updated for production IDs)
    private var adUnitId: String = DEFAULT_TEST_AD_UNIT_ID

    private var rewardedAd: RewardedAd? = null

    private val _isAdLoaded = MutableStateFlow(false)
    val isAdLoaded: StateFlow<Boolean> = _isAdLoaded.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _lastErrorMessage = MutableStateFlow<String?>(null)
    val lastErrorMessage: StateFlow<String?> = _lastErrorMessage.asStateFlow()

    private var isInitialized = false

    /**
     * Initializes Google Mobile Ads SDK and preloads a rewarded ad.
     */
    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            MobileAds.initialize(context) { status ->
                Log.d(TAG, "AdMob MobileAds initialized: $status")
            }
            isInitialized = true
            loadRewardedAd(context.applicationContext)
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MobileAds", e)
        }
    }

    /**
     * Set a custom or production Ad Unit ID.
     */
    fun setAdUnitId(customId: String) {
        if (customId.isNotBlank()) {
            adUnitId = customId
        }
    }

    /**
     * Preloads a Rewarded Ad into cache.
     */
    fun loadRewardedAd(
        context: Context,
        onLoaded: (() -> Unit)? = null,
        onFailed: ((String) -> Unit)? = null
    ) {
        if (_isLoading.value) {
            Log.d(TAG, "Ad is already loading. Skipping redundant load.")
            return
        }
        if (rewardedAd != null) {
            _isAdLoaded.value = true
            onLoaded?.invoke()
            return
        }

        _isLoading.value = true
        _lastErrorMessage.value = null
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            context,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Rewarded Ad loaded successfully.")
                    rewardedAd = ad
                    _isAdLoaded.value = true
                    _isLoading.value = false
                    _lastErrorMessage.value = null
                    onLoaded?.invoke()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    val errorMsg = "Ad failed to load: ${loadAdError.message} (code ${loadAdError.code})"
                    Log.w(TAG, errorMsg)
                    rewardedAd = null
                    _isAdLoaded.value = false
                    _isLoading.value = false
                    _lastErrorMessage.value = errorMsg
                    onFailed?.invoke(errorMsg)
                }
            }
        )
    }

    /**
     * Displays the Rewarded Ad voluntarily when user requests it.
     *
     * Rules enforced:
     * 1. Reward is given ONLY when AdMob confirms completion via onUserEarnedReward.
     * 2. If closed early, no reward is granted.
     * 3. Prevents duplicate rewards from the same impression.
     * 4. Preloads next ad automatically on dismissal.
     * 5. If ad is unavailable, invokes onAdUnavailable without crashing or blocking gameplay.
     */
    fun showRewardedAd(
        activity: Activity,
        rewardType: RewardType,
        onUserEarnedReward: (RewardType) -> Unit,
        onAdClosed: () -> Unit,
        onAdUnavailable: (String) -> Unit
    ) {
        val currentAd = rewardedAd

        if (currentAd == null) {
            Log.w(TAG, "Rewarded Ad is not ready to show.")
            onAdUnavailable("Rewarded Ad is preparing or currently unavailable. Please try again shortly.")
            // Try to reload
            loadRewardedAd(activity.applicationContext)
            return
        }

        // Flag to prevent duplicate rewards and ensure reward is only given if earned
        var userEarnedReward = false

        currentAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Rewarded ad showed fullscreen content.")
                _isAdLoaded.value = false
                rewardedAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Rewarded ad dismissed. User earned reward: $userEarnedReward")
                if (userEarnedReward) {
                    onUserEarnedReward(rewardType)
                }
                onAdClosed()
                // Preload the next rewarded ad
                loadRewardedAd(activity.applicationContext)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.e(TAG, "Ad failed to show: ${adError.message}")
                rewardedAd = null
                _isAdLoaded.value = false
                onAdUnavailable("Could not display ad: ${adError.message}")
                onAdClosed()
                loadRewardedAd(activity.applicationContext)
            }
        }

        currentAd.show(activity) { rewardItem ->
            Log.d(TAG, "User completed watching rewarded ad! Type: ${rewardItem.type}, Amount: ${rewardItem.amount}")
            userEarnedReward = true
        }
    }
}
