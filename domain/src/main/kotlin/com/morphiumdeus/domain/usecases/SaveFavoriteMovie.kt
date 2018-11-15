package com.morphiumdeus.domain.usecases

import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.common.Transformer
import com.morphiumdeus.domain.entities.CategoryEntity
import io.reactivex.Observable
import java.lang.IllegalArgumentException

/**
 * Created by Yossi Segev on 21/01/2018.
 */
class SaveFavoriteMovie(transformer: Transformer<Boolean>,
                        private val categoryCache: CategoryCache): UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_MOVIE_ENTITY = "param:movieEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {

        val movieEntity = data?.get(PARAM_MOVIE_ENTITY)

        movieEntity?.let {
            return Observable.fromCallable {
                val entity = it as CategoryEntity
                categoryCache.save(entity)
                return@fromCallable true
            }
        }?: return Observable.error({ IllegalArgumentException("MovieEntity must be provided.") })

    }

    fun save(logEntity: CategoryEntity): Observable<Boolean> {
        val data = HashMap<String, CategoryEntity>()
        data[PARAM_MOVIE_ENTITY] = logEntity
        return observable(data)
    }
}