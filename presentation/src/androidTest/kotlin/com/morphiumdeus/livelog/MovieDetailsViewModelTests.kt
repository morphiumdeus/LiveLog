package com.morphiumdeus.livelog

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import com.morphiumdeus.domain.CategoryCache
import com.morphiumdeus.domain.CategoriesRepository
import com.morphiumdeus.domain.common.DomainTestUtils
import com.morphiumdeus.domain.common.TestTransformer
import com.morphiumdeus.domain.entities.Optional
import com.morphiumdeus.domain.usecases.CheckFavoriteStatus
import com.morphiumdeus.domain.usecases.GetCategoryDetails
import com.morphiumdeus.domain.usecases.RemoveFavoriteMovie
import com.morphiumdeus.domain.usecases.SaveFavoriteMovie
import com.morphiumdeus.livelog.details.CategoryDetailsViewModel
import com.morphiumdeus.livelog.details.CategoryDetailsViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

/**
 * Created by Yossi Segev on 19/02/2018.
 */

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class MovieDetailsViewModelTests {

    private val testMovieId = 100
    private val movieEntityMovieMapper = CategoryEntityCategoryMapper()
    private lateinit var categoryDetailsViewModel: CategoryDetailsViewModel
    private lateinit var categoriesRepository: CategoriesRepository
    private lateinit var categoryCache: CategoryCache
    private lateinit var viewObserver: Observer<CategoryDetailsViewState>
    private lateinit var errorObserver: Observer<Throwable>
    private lateinit var favoriteStateObserver: Observer<Boolean>

    @Before
    @UiThreadTest
    fun before() {
        categoriesRepository = mock(CategoriesRepository::class.java)
        categoryCache = mock(CategoryCache::class.java)
        val getMovieDetails = GetCategoryDetails(TestTransformer(), categoriesRepository)
        val saveFavoriteMovie = SaveFavoriteMovie(TestTransformer(), categoryCache)
        val removeFavoriteMovie = RemoveFavoriteMovie(TestTransformer(), categoryCache)
        val checkFavoriteStatus = CheckFavoriteStatus(TestTransformer(), categoryCache)
        categoryDetailsViewModel = CategoryDetailsViewModel(
                getMovieDetails,
                movieEntityMovieMapper,
                testMovieId)

        viewObserver = mock(Observer::class.java) as Observer<CategoryDetailsViewState>
        favoriteStateObserver = mock(Observer::class.java) as Observer<Boolean>
        errorObserver = mock(Observer::class.java) as Observer<Throwable>
        categoryDetailsViewModel.viewState.observeForever(viewObserver)
        categoryDetailsViewModel.errorState.observeForever(errorObserver)
        categoryDetailsViewModel.favoriteState.observeForever(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun showsCorrectDetailsAndFavoriteState() {
        val movieEntity = DomainTestUtils.getTestCategoryEntity(testMovieId)
        `when`(categoriesRepository.getCategory(testMovieId)).thenReturn(Observable.just(
                Optional.of(movieEntity)
        ))
        `when`(categoryCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        categoryDetailsViewModel.getMovieDetails()

        val video = movieEntityMovieMapper.mapFrom(movieEntity)
        val expectedDetailsViewState = CategoryDetailsViewState(
                isLoading = false,
                name = video.name)

        verify(viewObserver).onChanged(expectedDetailsViewState)
        verify(favoriteStateObserver).onChanged(true)
        verifyZeroInteractions(errorObserver)
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenFailsToGetMovieFromRepository() {
        val movieEntity = DomainTestUtils.getTestCategoryEntity(testMovieId)
        val throwable = Throwable("ERROR!")

        `when`(categoriesRepository.getCategory(testMovieId)).thenReturn(Observable.error(throwable))
        `when`(categoryCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        categoryDetailsViewModel.getMovieDetails()

        verify(errorObserver).onChanged(throwable)
        verifyZeroInteractions(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenFailsToGetFavoriteState() {
        val movieEntity = DomainTestUtils.getTestCategoryEntity(testMovieId)

        `when`(categoriesRepository.getCategory(testMovieId)).thenReturn(Observable.just(Optional.empty()))
        `when`(categoryCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        categoryDetailsViewModel.getMovieDetails()

        verify(errorObserver).onChanged(any(Throwable::class.java))
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenGetMovieFromRepositoryReturnsEmptyOptional() {
        val movieEntity = DomainTestUtils.getTestCategoryEntity(testMovieId)
        val throwable = Throwable("ERROR!")

        `when`(categoriesRepository.getCategory(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))
        `when`(categoryCache.get(testMovieId)).thenReturn(Observable.error(throwable))

        categoryDetailsViewModel.getMovieDetails()

        verify(errorObserver).onChanged(throwable)
        verifyZeroInteractions(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun favoriteStateChangesAsExpected() {
        val movieEntity = DomainTestUtils.getTestCategoryEntity(testMovieId)
        `when`(categoriesRepository.getCategory(testMovieId)).thenReturn(Observable.just(
                Optional.of(movieEntity)
        ))

        `when`(categoryCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        categoryDetailsViewModel.getMovieDetails()
        verify(favoriteStateObserver).onChanged(true)

        verifyZeroInteractions(errorObserver)
    }


}