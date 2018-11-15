package com.morphiumdeus.livelog

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.common.DomainTestUtils
import com.morphiumdeus.domain.common.TestTransformer
import com.morphiumdeus.domain.usecases.GetPopularMovies
import com.morphiumdeus.livelog.popularmovies.CategoryListViewModel
import com.morphiumdeus.livelog.popularmovies.CategoryListViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class PopularMoviesViewModelTests {

    private val categoryEntityCategoryMapper = CategoryEntityCategoryMapper()
    private lateinit var categoryListViewModel: CategoryListViewModel
    private lateinit var categoriesRepository: CategoriesRepository
    private lateinit var viewObserver: Observer<CategoryListViewState>
    private lateinit var errorObserver: Observer<Throwable?>

    @Before
    @UiThreadTest
    fun before() {
        categoriesRepository = Mockito.mock(CategoriesRepository::class.java)
        val getPopularMoviesUseCase = GetPopularMovies(TestTransformer(), categoriesRepository)
        categoryListViewModel = CategoryListViewModel(getPopularMoviesUseCase, categoryEntityCategoryMapper)
        viewObserver = mock(Observer::class.java) as Observer<CategoryListViewState>
        errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        categoryListViewModel.viewState.observeForever(viewObserver)
        categoryListViewModel.errorState.observeForever(errorObserver)
    }

    @Test
    @UiThreadTest
    fun testInitialViewStateShowsLoading() {
        verify(viewObserver).onChanged(CategoryListViewState(showLoading = true, categories = null))
        verifyZeroInteractions(viewObserver)
    }

    @Test
    @UiThreadTest
    fun testShowingMoviesAsExpectedAndStopsLoading() {
        val categoryEntities = DomainTestUtils.generateCategoryEntityList()
        `when`(categoriesRepository.getCategories()).thenReturn(Observable.just(categoryEntities))
        categoryListViewModel.getCategoryList()
        val movies = categoryEntities.map { categoryEntityCategoryMapper.mapFrom(it) }

        verify(viewObserver).onChanged(CategoryListViewState(showLoading = false, categories = movies))
        verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testShowingErrorMessageWhenNeeded() {
        val throwable = Throwable("ERROR!")
        `when`(categoriesRepository.getCategories()).thenReturn(Observable.error(throwable))
        categoryListViewModel.getCategoryList()

        verify(viewObserver).onChanged(CategoryListViewState(showLoading = false, categories = null))
        verify(errorObserver).onChanged(throwable)
    }
}
