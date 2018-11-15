package com.morphiumdeus.domain

import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 25/01/2018.
 */
interface CategoriesRepository {
    fun getCategories(): Observable<List<CategoryEntity>>
    fun search(query: String): Observable<List<CategoryEntity>>
    fun getCategory(categoryId: Int): Observable<Optional<CategoryEntity>>
}