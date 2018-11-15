package com.morphiumdeus.livelog.di.details

import com.morphiumdeus.livelog.details.CategoryDetailsActivity
import dagger.Subcomponent

@DetailsScope
@Subcomponent(modules = [CategoryDetailsModule::class])
interface CategoryDetailsSubComponent {
    fun inject(categoryDetailsActivity: CategoryDetailsActivity)
}