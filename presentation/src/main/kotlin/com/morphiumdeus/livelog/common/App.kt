package com.morphiumdeus.livelog.common

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.morphiumdeus.livelog.R
import com.morphiumdeus.livelog.di.*
import com.morphiumdeus.livelog.di.details.CategoryDetailsModule
import com.morphiumdeus.livelog.di.details.CategoryDetailsSubComponent
import com.morphiumdeus.livelog.di.modules.*
import com.morphiumdeus.livelog.di.popular.PopularMoviesModule
import com.morphiumdeus.livelog.di.popular.PopularSubComponent
import com.morphiumdeus.livelog.di.search.SearchMoviesModule
import com.morphiumdeus.livelog.di.search.SearchSubComponent

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class App: Application() {

    lateinit var mainComponent: MainComponent
    private var popularMoviesComponent: PopularSubComponent? = null
    private var categoryDetailsComponent: CategoryDetailsSubComponent? = null
    private var searchMoviesComponent: SearchSubComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(getString(R.string.api_base_url), getString(R.string.api_key)))
                .dataModule(DataModule())
                .build()

    }

    fun createPopularComponenet(): PopularSubComponent {
        popularMoviesComponent = mainComponent.plus(PopularMoviesModule())
        return popularMoviesComponent!!
    }
    fun releasePopularComponent() {
        popularMoviesComponent = null
    }

    fun createDetailsComponent(): CategoryDetailsSubComponent {
        categoryDetailsComponent = mainComponent.plus(CategoryDetailsModule())
        return categoryDetailsComponent!!
    }
    fun releaseDetailsComponent() {
        categoryDetailsComponent = null
    }

    fun createSearchComponent(): SearchSubComponent {
        searchMoviesComponent = mainComponent.plus(SearchMoviesModule())
        return searchMoviesComponent!!
    }
    fun releaseSearchComponent() {
        searchMoviesComponent = null
    }
}