package com.morphiumdeus.livelog

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.common.DomainTestUtils
import com.morphiumdeus.domain.common.TestTransformer
import com.morphiumdeus.domain.usecases.SearchMovie
import com.morphiumdeus.livelog.search.SearchViewModel
import com.morphiumdeus.livelog.search.SearchViewState
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*


@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class SearchViewModelTests {

    private val testQuery = "this is a test query"
    private val movieEntityMovieMapper = CategoryEntityCategoryMapper()
    private lateinit var movieRepository: CategoriesRepository
    private lateinit var searchViewModel: SearchViewModel

    @Before
    @UiThreadTest
    fun before() {
        movieRepository = mock(CategoriesRepository::class.java)
        val searchMovie = SearchMovie(TestTransformer(), movieRepository)
        searchViewModel = SearchViewModel(searchMovie, movieEntityMovieMapper)
    }

    @Test
    @UiThreadTest
    fun testInitialViewState() {
        val viewObserver = mock(Observer::class.java) as Observer<SearchViewState>
        val errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        searchViewModel.viewState.observeForever(viewObserver)
        searchViewModel.errorState.observeForever(errorObserver)
        verify(viewObserver).onChanged(SearchViewState(
                isLoading = false,
                categories = null,
                showNoResultsMessage = false,
                lastSearchedQuery = null))

        verifyZeroInteractions(errorObserver)
    }

    @Test
    @UiThreadTest
    fun testSearchWithResultsShowsCorrectViewStates() {

        val movieEntities = DomainTestUtils.generateCategoryEntityList()
        `when`(movieRepository.search(testQuery)).thenReturn(Observable.just(movieEntities))
        val viewObserver = ChangeHistoryObserver<SearchViewState>()
        val errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        searchViewModel.viewState.observeForever(viewObserver)
        searchViewModel.errorState.observeForever(errorObserver)
        searchViewModel.search(testQuery)

        val loadingViewState = SearchViewState(
                isLoading = true,
                showNoResultsMessage = false,
                categories = null)

        val resultsViewState = SearchViewState(
                isLoading = false,
                lastSearchedQuery = testQuery,
                categories = movieEntities.map { movieEntityMovieMapper.mapFrom(it) }
        )
        assertEquals(viewObserver.get(1), loadingViewState)
        assertEquals(viewObserver.get(2), resultsViewState)
        verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testSearchWithNoResultsShowsCorrectViewStates() {

        `when`(movieRepository.search(testQuery)).thenReturn(Observable.just(emptyList()))
        val viewObserver = ChangeHistoryObserver<SearchViewState>()
        val errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        searchViewModel.viewState.observeForever(viewObserver)
        searchViewModel.errorState.observeForever(errorObserver)
        searchViewModel.search(testQuery)

        val loadingViewState = SearchViewState(
                isLoading = true,
                showNoResultsMessage = false,
                lastSearchedQuery = null,
                categories = null)

        val resultsViewState = SearchViewState(
                isLoading = false,
                lastSearchedQuery = testQuery,
                showNoResultsMessage = true,
                categories = emptyList()
        )
        assertEquals(viewObserver.get(1), loadingViewState)
        assertEquals(viewObserver.get(2), resultsViewState)
        verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testQueryStringIsEmptyShowsCorrectViewStates() {

        `when`(movieRepository.search(testQuery)).thenReturn(Observable.just(emptyList()))
        val viewObserver = ChangeHistoryObserver<SearchViewState>()
        val errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        searchViewModel.viewState.observeForever(viewObserver)
        searchViewModel.errorState.observeForever(errorObserver)
        searchViewModel.search("")

        val loadingViewState = SearchViewState(
                isLoading = false,
                showNoResultsMessage = false,
                lastSearchedQuery = "",
                categories = null)
        assertEquals(viewObserver.get(1), loadingViewState)
        verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testErrorShowsCorrectViewStates() {
        val throwable = Throwable("ERROR!")
        `when`(movieRepository.search(testQuery)).thenReturn(Observable.error(throwable))
        val viewObserver = ChangeHistoryObserver<SearchViewState>()
        val errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        searchViewModel.viewState.observeForever(viewObserver)
        searchViewModel.errorState.observeForever(errorObserver)
        searchViewModel.search(testQuery)

        val loadingViewState = SearchViewState(
                isLoading = false,
                showNoResultsMessage = false,
                lastSearchedQuery = testQuery,
                categories = null)
        assertEquals(viewObserver.get(2), loadingViewState)
        verify(errorObserver).onChanged(throwable)
    }
}