package com.morphiumdeus.livelog.di

import com.morphiumdeus.livelog.di.modules.*
import com.morphiumdeus.livelog.di.details.CategoryDetailsModule
import com.morphiumdeus.livelog.di.details.CategoryDetailsSubComponent
import com.morphiumdeus.livelog.di.popular.PopularMoviesModule
import com.morphiumdeus.livelog.di.popular.PopularSubComponent
import com.morphiumdeus.livelog.di.search.SearchMoviesModule
import com.morphiumdeus.livelog.di.search.SearchSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DataModule::class)
])

interface MainComponent {
    fun plus(popularMoviesModule: PopularMoviesModule): PopularSubComponent
    fun plus(categoryDetailsModule: CategoryDetailsModule): CategoryDetailsSubComponent
    fun plus(searchMoviesModule: SearchMoviesModule): SearchSubComponent
}