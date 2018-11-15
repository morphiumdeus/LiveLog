package com.morphiumdeus.domain.usecases

import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.common.Transformer
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
open class GetPopularMovies(transformer: Transformer<List<CategoryEntity>>,
                            private val categoriesRepository: CategoriesRepository) : UseCase<List<CategoryEntity>>(transformer) {
    override fun createObservable(data: Map<String, Any>?): Observable<List<CategoryEntity>> {
        return categoriesRepository.getCategories()
    }

}