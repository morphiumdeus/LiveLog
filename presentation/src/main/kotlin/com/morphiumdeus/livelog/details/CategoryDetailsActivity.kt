package com.morphiumdeus.livelog.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.transition.TransitionManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.morphiumdeus.livelog.R
import com.morphiumdeus.livelog.common.App
import com.morphiumdeus.livelog.common.SimpleTransitionEndedCallback
import kotlinx.android.synthetic.main.activity_movie_details.*
import javax.inject.Inject

class CategoryDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: CategoryDetailsVMFactory

    private lateinit var detailsViewModel: CategoryDetailsViewModel
    private lateinit var name: TextView

    companion object {
        private const val CATEGORY_ID: String = "extra_category_id"

        fun newIntent(context: Context, categoryId: Int): Intent {
            val i = Intent(context, CategoryDetailsActivity::class.java)
            i.putExtra(CATEGORY_ID, categoryId)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        postponeEnterTransition()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN

        (application as App).createDetailsComponent().inject(this)

        factory.categoryId = intent.getIntExtra(CATEGORY_ID, 0)
        detailsViewModel = ViewModelProviders.of(this, factory).get(CategoryDetailsViewModel::class.java)

        window.sharedElementEnterTransition.addListener(SimpleTransitionEndedCallback {
            observeViewState()
        })

        // If we don't have any entering transition
        if (savedInstanceState != null) {
            observeViewState()
        } else {
            detailsViewModel.getMovieDetails()
        }
    }

    private fun observeViewState() {
        detailsViewModel.viewState.observe(this, Observer { viewState -> handleViewState(viewState) })
       detailsViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: CategoryDetailsViewState?) {
        if (state == null) return

        name.text = state.name

        val transition = Slide()
        transition.duration = 750
        TransitionManager.beginDelayedTransition(details_root_view, transition)
        name.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseDetailsComponent()
    }
}
