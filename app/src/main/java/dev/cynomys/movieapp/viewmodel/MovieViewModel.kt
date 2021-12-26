package dev.cynomys.movieapp.viewmodel

import androidx.lifecycle.*
import dev.cynomys.movieapp.businesslogic.repositories.MovieApiRepo
import dev.cynomys.movieapp.model.Movie
import kotlinx.coroutines.Dispatchers

class MovieViewModel constructor(private val respository: MovieApiRepo) : ViewModel() {
    private val _loadingStatus = MutableLiveData(true)
    val loadingStatus: LiveData<Boolean> get() = _loadingStatus

    fun getMovie() = liveData<Movie>(viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingStatus.value = true

    }
}