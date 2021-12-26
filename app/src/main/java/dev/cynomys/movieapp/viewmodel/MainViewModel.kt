package dev.cynomys.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cynomys.movieapp.businesslogic.repositories.MovieApiRepo
import dev.cynomys.movieapp.model.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieApiRepo) :
    ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList

    private val _loadingStatus = MutableLiveData(true)
    val loadingStatus: LiveData<Boolean> get() = _loadingStatus

    init {
        getMovieList()
    }

    fun getMovieList() {
        viewModelScope.launch {
            _loadingStatus.value = true
            _movieList.value = repository.getMovieList().results
            _loadingStatus.value = false
        }
    }
}