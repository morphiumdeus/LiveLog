package com.morphiumdeus.domain.usecases

import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.common.Transformer
import io.reactivex.Observable
import java.lang.IllegalArgumentException

/**
 * Created by Yossi Segev on 09/02/2018.
 */

class CheckFavoriteStatus(transformer: Transformer<Boolean>,
                          private val categoryCache: CategoryCache) : UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_MOVIE_ENTITY = "param:movieEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val movieId = data?.get(PARAM_MOVIE_ENTITY)
        movieId?.let {
            return categoryCache.get(it as Int).flatMap { optionalMovieEntity ->
                return@flatMap Observable.just(optionalMovieEntity.hasValue())
            }
        } ?: return Observable.error({ IllegalArgumentException("MovieId must be provided.") })
    }

    fun check(movieId: Int): Observable<Boolean> {
        val data = HashMap<String, Int>()
        data[PARAM_MOVIE_ENTITY] = movieId
        return observable(data)
    }

}