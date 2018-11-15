package com.morphiumdeus.livelog.di.search

import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.usecases.SearchMovie
import com.morphiumdeus.livelog.CategoryEntityCategoryMapper
import com.morphiumdeus.livelog.common.ASyncTransformer
import com.morphiumdeus.livelog.search.SearchVMFactory
import dagger.Module
import dagger.Provides

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@Module
class SearchMoviesModule {

    @Provides
    fun provideSearchMovieUseCase(categoriesRepository: CategoriesRepository): SearchMovie {
        return SearchMovie(ASyncTransformer(), categoriesRepository)
    }

    @Provides
    fun provideSearchVMFactory(useCase: SearchMovie, mapper: CategoryEntityCategoryMapper): SearchVMFactory {
        return SearchVMFactory(useCase, mapper)
    }
}