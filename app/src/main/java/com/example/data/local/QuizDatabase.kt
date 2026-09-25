package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [QuestionEntity::class, QuestionHistoryEntity::class, UserProgressEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun questionHistoryDao(): QuestionHistoryDao
    abstract fun userProgressDao(): UserProgressDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getInstance(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "history_quiz_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
