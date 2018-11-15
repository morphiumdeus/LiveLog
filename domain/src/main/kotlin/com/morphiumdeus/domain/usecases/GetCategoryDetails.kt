package com.morphiumdeus.domain.usecases

import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.common.Transformer
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.Optional
import io.reactivex.Observable
import java.lang.IllegalArgumentException

class GetCategoryDetails(
        transformer: Transformer<Optional<CategoryEntity>>,
        private val categoriesRepository: CategoriesRepository) : UseCase<Optional<CategoryEntity>>(transformer) {

    companion object {
        private const val PARAM_CATEGORY_ENTITY = "param:categoryEntity"
    }

    fun getById(categoryId: Int): Observable<Optional<CategoryEntity>> {
        val data = HashMap<String, Int>()
        data[PARAM_CATEGORY_ENTITY] = categoryId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Optional<CategoryEntity>> {
        val categoryId = data?.get(PARAM_CATEGORY_ENTITY)
        categoryId?.let {
            return categoriesRepository.getCategory(it as Int)
        } ?: return Observable.error({ IllegalArgumentException("CategoryId must be provided.") })
    }
}