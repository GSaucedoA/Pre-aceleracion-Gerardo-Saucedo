package dev.cynomys.movieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.cynomys.movieapp.movie.MovieRemoteDataSource
import dev.cynomys.movieapp.movie.MovieRepository

class MainViewModelFactory(private val page: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val remoteDataSource = MovieRemoteDataSource()
        val repository = MovieRepository(remoteDataSource)
        return MainViewModel(repository, page) as T
    }
}