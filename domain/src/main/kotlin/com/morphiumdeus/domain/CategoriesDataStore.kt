package com.morphiumdeus.domain

import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
interface CategoriesDataStore {

    fun getCategoryById(categoryId: Int): Observable<Optional<CategoryEntity>>
    fun getCategories(): Observable<List<CategoryEntity>>
    fun search(query: String): Observable<List<CategoryEntity>>
}