package com.morphiumdeus.livelog.di.popular

import com.morphiumdeus.livelog.popularmovies.CategoryListFragment
import dagger.Subcomponent

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@Subcomponent(modules = [PopularMoviesModule::class])
interface PopularSubComponent {
    fun inject(categoryListFragment: CategoryListFragment)
}