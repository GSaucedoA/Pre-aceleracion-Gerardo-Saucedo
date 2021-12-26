package dev.cynomys.movieapp.businesslogic.repositories

import dev.cynomys.movieapp.model.FullMovie
import dev.cynomys.movieapp.model.MovieListResponse

interface MovieApiRepo {
    suspend fun getMovieList(page: Int): MovieListResponse
    suspend fun getmovie(id: Int): FullMovie
}