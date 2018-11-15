package com.morphiumdeus.livelog.di.popular

import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.usecases.GetPopularMovies
import com.morphiumdeus.livelog.CategoryEntityCategoryMapper
import com.morphiumdeus.livelog.common.ASyncTransformer
import com.morphiumdeus.livelog.popularmovies.CategoryListVMFactory
import dagger.Module
import dagger.Provides

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@PopularScope
@Module
class PopularMoviesModule {
    @Provides
    fun provideGetPopularMoviesUseCase(categoriesRepository: CategoriesRepository): GetPopularMovies {
        return GetPopularMovies(ASyncTransformer(), categoriesRepository)
    }

    @Provides
    fun providePopularMoviesVMFactory(useCase: GetPopularMovies, mapper: CategoryEntityCategoryMapper): CategoryListVMFactory {
        return CategoryListVMFactory(useCase, mapper)
    }
}