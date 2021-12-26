package dev.cynomys.movieapp.businesslogic.repositories

import dev.cynomys.movieapp.model.MovieListResponse

interface MovieApiRepo {
    suspend fun getMovieList(): MovieListResponse
}