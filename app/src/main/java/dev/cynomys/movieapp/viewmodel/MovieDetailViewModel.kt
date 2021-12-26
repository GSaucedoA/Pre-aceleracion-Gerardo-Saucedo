package dev.cynomys.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cynomys.movieapp.businesslogic.repositories.MovieApiRepo
import dev.cynomys.movieapp.model.FullMovie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieApiRepo) : ViewModel() {
    private val _movie = MutableLiveData<FullMovie>()
    val movie: LiveData<FullMovie> get() = _movie

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun fetchMovie(id: Int) {
        viewModelScope.launch {
            _loadingState.value = true
            _movie.value = repository.getmovie(id)
            _loadingState.value = false
        }
    }
}