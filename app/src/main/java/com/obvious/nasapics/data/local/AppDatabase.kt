package com.obvious.nasapics.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obvious.nasapics.data.models.ImageResult


@Database(entities = [ImageResult::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun imageResultDao():ImageResultDao
}