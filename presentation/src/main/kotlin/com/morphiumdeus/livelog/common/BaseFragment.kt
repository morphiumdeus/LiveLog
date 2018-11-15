package com.morphiumdeus.livelog.common

import android.app.ActivityOptions
import android.support.v4.app.Fragment
import android.util.Pair
import android.view.View
import com.morphiumdeus.livelog.R
import com.morphiumdeus.livelog.details.CategoryDetailsActivity
import com.morphiumdeus.livelog.entities.Category

/**
 * Created by Yossi Segev on 19/02/2018.
 */
open class BaseFragment: Fragment() {

    protected fun navigateToMovieDetailsScreen(category: Category, view: View) {
        var activityOptions: ActivityOptions? = null

        val imageForTransition: View? = view.findViewById(R.id.image)
        imageForTransition?.let {
            val posterSharedElement: Pair<View, String> = Pair.create(it, getString(R.string.transition_poster))
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, posterSharedElement)
        }
        startActivity(CategoryDetailsActivity.newIntent(
                context!!,
                category.id), activityOptions?.toBundle())

        activity?.overridePendingTransition(0, 0)
    }
}