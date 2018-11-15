package com.morphiumdeus.data.db

import android.arch.persistence.room.*
import com.morphiumdeus.data.entities.CategoryData


@Dao
interface CategoryDataDao {

    @Query("SELECT * FROM categoryData")
    fun getCategoryData(): List<CategoryData>

    @Query("SELECT * FROM categoryData WHERE id=:categoryId")
    fun get(categoryId: Int): CategoryData?

    @Query("SELECT * FROM categoryData WHERE name LIKE :query")
    fun search(query: String): List<CategoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCategory(category: CategoryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCategories(categories: List<CategoryData>)

    @Delete
    fun removeCategory(category: CategoryData)

    @Query("DELETE FROM categoryData")
    fun clear()

}