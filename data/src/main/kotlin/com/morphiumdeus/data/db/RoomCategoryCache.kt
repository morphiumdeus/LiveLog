package com.morphiumdeus.data.db

import com.morphiumdeus.data.entities.CategoryData
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.Optional
import io.reactivex.Observable

class RoomCategoryCache(database: CategoryDataDatabase,
                        private val entityToDataMapper: Mapper<CategoryEntity, CategoryData>,
                        private val dataToEntityMapper: Mapper<CategoryData, CategoryEntity>) : CategoryCache {
    private val dao: CategoryDataDao = database.getCategoryDataDao()

    override fun clear() {
        dao.clear()
    }

    override fun save(categoryEntity: CategoryEntity) {
        dao.saveCategory(entityToDataMapper.mapFrom(categoryEntity))
    }

    override fun remove(categoryEntity: CategoryEntity) {
        dao.removeCategory(entityToDataMapper.mapFrom(categoryEntity))
    }

    override fun saveAll(categoryEntities: List<CategoryEntity>) {
        dao.saveAllCategories(categoryEntities.map { entityToDataMapper.mapFrom(it) })
    }

    override fun getAll(): Observable<List<CategoryEntity>> {
        return Observable.fromCallable { dao.getCategoryData().map { dataToEntityMapper.mapFrom(it) } }
    }

    override fun get(categoryId: Int): Observable<Optional<CategoryEntity>> {
        return Observable.fromCallable {
            val categoryData = dao.get(categoryId)
            categoryData?.let {
                Optional.of(dataToEntityMapper.mapFrom(it))
            } ?: Optional.empty()
        }
    }

    override fun isEmpty(): Observable<Boolean> {
        return Observable.fromCallable { dao.getCategoryData().isEmpty() }
    }

    override fun search(query: String): Observable<List<CategoryEntity>> {
        val searchQuery = "%$query%"
        return Observable.fromCallable {
            dao.search(searchQuery).map { dataToEntityMapper.mapFrom(it) }
        }
    }
}