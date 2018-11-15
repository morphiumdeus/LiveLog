package com.morphiumdeus.livelog.search

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.usecases.SearchMovie
import com.morphiumdeus.livelog.common.BaseViewModel
import com.morphiumdeus.livelog.common.SingleLiveEvent
import com.morphiumdeus.livelog.entities.Category

/**
 * Created by Yossi Segev on 11/02/2018.
 */
class SearchViewModel(private val searchMovie: SearchMovie,
                      private val entityLogMapper: Mapper<CategoryEntity, Category>) : BaseViewModel() {

    var viewState: MutableLiveData<SearchViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = SearchViewState()
    }

    fun search(query: String) {

        errorState.value = null

        if (query.isEmpty()) {
            viewState.value = viewState.value?.copy(
                    isLoading = false,
                    showNoResultsMessage = false,
                    lastSearchedQuery = query,
                    categories = null)
        } else {
            viewState.value = viewState.value?.copy(
                    isLoading = true,
                    showNoResultsMessage = false)

            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        Log.d(javaClass.simpleName, "Searching $query")

        addDisposable(searchMovie.search(query)
                .flatMap { entityLogMapper.observable(it) }
                .subscribe({ movies ->
                    viewState.value = viewState.value?.copy(
                            isLoading = false,
                            categories = movies,
                            lastSearchedQuery = query,
                            showNoResultsMessage = movies.isEmpty())

                }, {
                    viewState.value = viewState.value?.copy(
                            isLoading = false,
                            categories = null,
                            lastSearchedQuery = query
                    )
                    errorState.value = it
                }))
    }


}