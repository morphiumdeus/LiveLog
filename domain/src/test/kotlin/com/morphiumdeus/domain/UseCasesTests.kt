package com.morphiumdeus.domain

/**
 * Created by Yossi Segev on 06/01/2018.
 */
import com.morphiumdeus.domain.common.DomainTestUtils
import com.morphiumdeus.domain.common.DomainTestUtils.Companion.generateCategoryEntityList
import com.morphiumdeus.domain.common.TestCategoryCache
import com.morphiumdeus.domain.common.TestTransformer
import com.morphiumdeus.domain.entities.Optional
import com.morphiumdeus.domain.usecases.*
import io.reactivex.Observable
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class UseCasesTests {

    @Test
    fun getMovieDetailsById() {
        val categoryEntity = DomainTestUtils.getTestCategoryEntity(100)
        val categoryRepository = Mockito.mock(CategoriesRepository::class.java)
        val getMovieDetails = GetCategoryDetails(TestTransformer(), categoryRepository)

        Mockito.`when`(categoryRepository.getCategory(100)).thenReturn(Observable.just(Optional.of(categoryEntity)))
    }

    @Test
    fun getPopularMovies() {
        val movieRepository = Mockito.mock(CategoriesRepository::class.java)
        Mockito.`when`(movieRepository.getCategories()).thenReturn(Observable.just(generateCategoryEntityList()))
        val getPopularMovies = GetPopularMovies(TestTransformer(), movieRepository)
        getPopularMovies.observable().test()
                .assertValue { results -> results.size == 5 }
                .assertComplete()
    }

    @Test
    fun getPopularMoviesNoResultsReturnsEmpty() {
        val movieRepository = Mockito.mock(CategoriesRepository::class.java)
        Mockito.`when`(movieRepository.getCategories()).thenReturn(Observable.just(emptyList()))
        val getPopularMovies = GetPopularMovies(TestTransformer(), movieRepository)
        getPopularMovies.observable().test()
                .assertValue { results -> results.isEmpty() }
                .assertComplete()
    }

    @Test
    fun saveMovieToFavorites() {
        val moviesCache = TestCategoryCache()
        val saveFavoriteMovie = SaveFavoriteMovie(TestTransformer(), moviesCache)
        val movieEntity = DomainTestUtils.getTestCategoryEntity(1)
        saveFavoriteMovie.save(movieEntity).test()
                .assertValue { result -> result }
                .assertComplete()
        moviesCache.get(movieEntity.id).test()
                .assertValue { optionalMovieEntity ->
                    optionalMovieEntity.hasValue()
                            && optionalMovieEntity.value?.id == movieEntity.id
                }
    }

    @Test
    fun getFavoriteMovies() {
        val moviesCache = Mockito.mock(CategoryCache::class.java)
        Mockito.`when`(moviesCache.getAll()).thenReturn(Observable.just(generateCategoryEntityList()))
        val getFavoriteMovies = GetFavoriteMovies(TestTransformer(), moviesCache)
        getFavoriteMovies.observable().test()
                .assertValue { results -> results.size == 5 }
                .assertComplete()
    }

    @Test
    fun removeFavoriteMovie() {
        val moviesCache = TestCategoryCache()
        val saveFavoriteMovie = SaveFavoriteMovie(TestTransformer(), moviesCache)
        val removeFavoriteMovies = RemoveFavoriteMovie(TestTransformer(), moviesCache)
        val movieEntity = DomainTestUtils.getTestCategoryEntity(1)
        saveFavoriteMovie.save(movieEntity)
        assertNotNull(moviesCache.get(movieEntity.id))
        removeFavoriteMovies.remove(movieEntity).test()
                .assertValue { returnedValue -> !returnedValue }
                .assertComplete()

        moviesCache.get(movieEntity.id).test()
                .assertValue { optionalEntity -> !optionalEntity.hasValue() }
    }

    @Test
    fun searchMovies() {
        val movieRepository = Mockito.mock(CategoriesRepository::class.java)
        val searchMovie = SearchMovie(TestTransformer(), movieRepository)
        `when`(movieRepository.search("test query")).thenReturn(Observable.just(generateCategoryEntityList()))
        searchMovie.search("test query").test()
                .assertComplete()
                .assertValue { results -> results.size == 5 }
    }

    @Test
    fun testCheckFavoriteStatus() {
        val movieCache = mock(CategoryCache::class.java)
        `when`(movieCache.get(99)).thenReturn(Observable.just(Optional.empty()))
        `when`(movieCache.get(100)).thenReturn(Observable.just(Optional.of(DomainTestUtils.getTestCategoryEntity(100))))
        val checkFavoriteStatus = CheckFavoriteStatus(TestTransformer(), movieCache)
        checkFavoriteStatus.check(99).test()
                .assertValue { result -> !result }
                .assertComplete()
        checkFavoriteStatus.check(100).test()
                .assertValue { result -> result }
                .assertComplete()


    }

}