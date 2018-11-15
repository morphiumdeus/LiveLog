package com.morphiumdeus.data.api

import com.morphiumdeus.data.entities.CategoryData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/{id}?append_to_response=videos,reviews")
    fun getCategoryById(@Path("id") categoryId: Int): Observable<CategoryData>

    @GET("movie/popular") ///movie/now_playing
    fun getCategories(): Observable<CategoryListResult>

    @GET("search/movie")
    fun searchCategories(@Query("query") query: String): Observable<CategoryListResult>

}