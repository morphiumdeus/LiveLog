package com.morphiumdeus.domain.usecases

import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.common.Transformer
import com.morphiumdeus.domain.entities.CategoryEntity
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 21/01/2018.
 */
class GetFavoriteMovies(transformer: Transformer<List<CategoryEntity>>,
                        private val categoryCache: CategoryCache): UseCase<List<CategoryEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<CategoryEntity>> {
        return categoryCache.getAll()
    }

}