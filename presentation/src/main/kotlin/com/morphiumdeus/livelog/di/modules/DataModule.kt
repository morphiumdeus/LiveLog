package com.morphiumdeus.livelog.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.morphiumdeus.data.api.Api
import com.morphiumdeus.data.db.CategoryDataDatabase
import com.morphiumdeus.data.db.RoomCategoryCache
import com.morphiumdeus.data.mappers.CategoryDataEntityMapper
import com.morphiumdeus.data.mappers.CategoryEntityDataMapper
import com.morphiumdeus.data.repositories.*
import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.livelog.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 13/11/2017.
 */
@Module
@Singleton
class DataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): CategoryDataDatabase {
        return Room.databaseBuilder(
                context,
                CategoryDataDatabase::class.java,
                "movies_db").build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: Api,
                               @Named(DI.inMemoryCache) cache: CategoryCache): CategoriesRepository {

        val cachedMoviesDataStore = CachedCategoriesDataStore(cache)
        val remoteMoviesDataStore = RemoteCategoriesDataStore(api) //TODO: remote?
        return CategoriesRepositoryImpl(cachedMoviesDataStore)
    }

    @Singleton
    @Provides
    @Named(DI.inMemoryCache)
    fun provideInMemoryMoviesCache(): CategoryCache {
        return MemoryCategoryCache()
    }

    @Singleton
    @Provides
    @Named(DI.favoritesCache)
    fun provideFavoriteMoviesCache(categoryDataDatabase: CategoryDataDatabase,
                                   entityDataMapper: CategoryEntityDataMapper,
                                   dataEntityMapper: CategoryDataEntityMapper): CategoryCache {
        return RoomCategoryCache(categoryDataDatabase, entityDataMapper, dataEntityMapper)
    }
}