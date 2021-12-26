package dev.cynomys.movieapp.businesslogic.repositories

import dev.cynomys.movieapp.businesslogic.api.MovieApiService
import dev.cynomys.movieapp.model.MovieListResponse

class MovieApiRepoImpl constructor(private val apiService: MovieApiService): MovieApiRepo {
    override suspend fun getMovieList(): MovieListResponse = apiService.getMovieList()
}