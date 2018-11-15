package com.morphiumdeus.livelog.di.details

import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.usecases.CheckFavoriteStatus
import com.morphiumdeus.domain.usecases.GetCategoryDetails
import com.morphiumdeus.domain.usecases.RemoveFavoriteMovie
import com.morphiumdeus.domain.usecases.SaveFavoriteMovie
import com.morphiumdeus.livelog.CategoryEntityCategoryMapper
import com.morphiumdeus.livelog.common.ASyncTransformer
import com.morphiumdeus.livelog.details.CategoryDetailsVMFactory
import com.morphiumdeus.livelog.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CategoryDetailsModule {

    @Provides
    fun provideGetCategoryDetailsUseCase(categoriesRepository: CategoriesRepository): GetCategoryDetails {
        return GetCategoryDetails(ASyncTransformer(), categoriesRepository)
    }

    @Provides
    fun provideCategoryDetailsVMFactory(getCategoryDetails: GetCategoryDetails,
                                        mapper: CategoryEntityCategoryMapper): CategoryDetailsVMFactory {
        return CategoryDetailsVMFactory(getCategoryDetails, mapper)
    }
}