package com.morphiumdeus.domain.usecases

import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.common.Transformer
import com.morphiumdeus.domain.entities.CategoryEntity
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/02/2018.
 */
class SearchMovie(transformer: Transformer<List<CategoryEntity>>,
                  private val categoriesRepository: CategoriesRepository) : UseCase<List<CategoryEntity>>(transformer) {

    companion object {
        private const val PARAM_SEARCH_QUERY = "param:search_query"
    }

    fun search(query: String): Observable<List<CategoryEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_SEARCH_QUERY] = query
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<CategoryEntity>> {
        val query = data?.get(PARAM_SEARCH_QUERY)
        query?.let {
            return categoriesRepository.search(it as String)
        } ?: return Observable.just(emptyList())
    }

}