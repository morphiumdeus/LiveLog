package com.morphiumdeus.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.morphiumdeus.data.entities.CategoryData

@Database(entities = arrayOf(CategoryData::class), version = 1)
abstract class CategoryDataDatabase: RoomDatabase() {
    abstract fun getCategoryDataDao(): CategoryDataDao
}