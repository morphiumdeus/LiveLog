package com.morphiumdeus.livelog.popularmovies

import com.morphiumdeus.livelog.entities.Category

/**
 * Created by Yossi Segev on 06/01/2018.
 */
data class CategoryListViewState(
        var showLoading: Boolean = true,
        var categories: List<Category>? = null
)
