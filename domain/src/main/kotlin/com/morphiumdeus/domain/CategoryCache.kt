package com.morphiumdeus.domain

import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.Optional
import io.reactivex.Observable

interface CategoryCache {

    fun clear()
    fun save(categoryEntity: CategoryEntity)
    fun remove(categoryEntity: CategoryEntity)
    fun saveAll(categoryEntities: List<CategoryEntity>)
    fun getAll(): Observable<List<CategoryEntity>>
    fun get(categoryId: Int): Observable<Optional<CategoryEntity>>
    fun search(query: String): Observable<List<CategoryEntity>>
    fun isEmpty(): Observable<Boolean>
}