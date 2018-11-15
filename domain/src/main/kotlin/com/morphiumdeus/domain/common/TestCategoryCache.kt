package com.morphiumdeus.domain.common

import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 13/11/2017.
 */
class TestCategoryCache : CategoryCache {

    private val categories: HashMap<Int, CategoryEntity> = HashMap()

    override fun search(query: String): Observable<List<CategoryEntity>> {
        return Observable.just(categories.values.toList())
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.fromCallable {  categories.isEmpty() }
    }

    override fun remove(categoryEntity: CategoryEntity) {
        categories.remove(categoryEntity.id)
    }

    override fun clear() {
        categories.clear()
    }

    override fun save(categoryEntity: CategoryEntity) {
        categories[categoryEntity.id] = categoryEntity
    }

    override fun saveAll(categoryEntities: List<CategoryEntity>) {
        categoryEntities.forEach { categoryEntity -> this.categories[categoryEntity.id] = categoryEntity }
    }

    override fun getAll(): Observable<List<CategoryEntity>> {
        return Observable.just(categories.values.toList())
    }

    override fun get(categoryId: Int): Observable<Optional<CategoryEntity>> {
        return Observable.just(Optional.of(categories[categoryId]))
    }
}

