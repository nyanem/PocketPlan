package com.example.pocketplan

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pocketplan.User

@Database(entities = [CategoryEntity::class, User::class], version = 2)

abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "category_database"
                ).build()
                INSTANCE = instance
                instance

            }

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // ← Important!
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}



