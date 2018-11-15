package com.morphiumdeus.livelog.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.usecases.SearchMovie
import com.morphiumdeus.livelog.entities.Category

/**
 * Created by Yossi Segev on 12/02/2018.
 */
class SearchVMFactory(val searchMovie: SearchMovie,
                      val mapper: Mapper<CategoryEntity, Category>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchMovie, mapper) as T
    }

}