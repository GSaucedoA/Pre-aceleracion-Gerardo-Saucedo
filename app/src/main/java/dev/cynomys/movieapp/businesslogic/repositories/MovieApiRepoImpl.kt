package dev.cynomys.movieapp.businesslogic.repositories

import dev.cynomys.movieapp.businesslogic.api.MovieApiService
import dev.cynomys.movieapp.model.FullMovie
import dev.cynomys.movieapp.model.MovieListResponse
import javax.inject.Inject

class MovieApiRepoImpl @Inject constructor(private val apiService: MovieApiService) : MovieApiRepo {
    override suspend fun getMovieList(page: Int): MovieListResponse =
        apiService.getMovieList(page = page)

    override suspend fun getmovie(id: Int): FullMovie = apiService.getMovie(id)
}