package com.morphiumdeus.livelog.search

import com.morphiumdeus.livelog.entities.Category

/**
 * Created by Yossi Segev on 11/02/2018.
 */
data class SearchViewState(
        val isLoading: Boolean = false,
        val categories: List<Category>? = null,
        val lastSearchedQuery: String? = null,
        val showNoResultsMessage: Boolean = false
)