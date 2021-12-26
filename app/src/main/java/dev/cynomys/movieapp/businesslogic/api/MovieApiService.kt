package dev.cynomys.movieapp.businesslogic.api

import dev.cynomys.movieapp.BuildConfig
import dev.cynomys.movieapp.model.Movie
import dev.cynomys.movieapp.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int, @Query("api_key") apiKey: String): Movie
}