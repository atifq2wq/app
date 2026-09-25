package com.example

import com.example.model.LevelCatalog
import com.example.model.UserProgress
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testUserStartsAtLevel1() {
    val progress = UserProgress()
    assertEquals("New user must start at Level 1", 1, progress.level)
    assertEquals("Level 1 must be unlocked by default", 1, progress.unlockedLevel)
    assertTrue("Level 1 is playable initially", progress.isLevelUnlocked(1))
    assertFalse("Level 2 must be locked initially", progress.isLevelUnlocked(2))
    assertFalse("Level 3 must be locked initially", progress.isLevelUnlocked(3))
  }

  @Test
  fun testLevelProgressionUnlocksNextLevel() {
    val initial = UserProgress()
    assertFalse(initial.isLevelUnlocked(2))

    // After completing Level 1, unlockedLevel becomes 2
    val afterLvl1 = initial.copy(unlockedLevel = 2, completedLevelsString = "1")
    assertTrue("Level 1 remains unlocked", afterLvl1.isLevelUnlocked(1))
    assertTrue("Level 2 is now unlocked", afterLvl1.isLevelUnlocked(2))
    assertFalse("Level 3 is still locked", afterLvl1.isLevelUnlocked(3))
    assertTrue("Level 1 is marked completed", afterLvl1.isLevelCompleted(1))
    assertFalse("Level 2 is not yet marked completed", afterLvl1.isLevelCompleted(2))
  }

  @Test
  fun testLevelCatalogStructure() {
    val levels = LevelCatalog.levels
    assertEquals(20, levels.size)
    assertEquals(1, levels.first().levelNumber)
    assertEquals("The Dawn of Civilizations", levels.first().title)
    assertTrue(levels.all { it.questionCount in 5..10 })
    assertTrue(levels.all { it.passingPercentage in 60..85 })
    assertTrue(levels.all { it.xpReward >= 150 })
  }
}

