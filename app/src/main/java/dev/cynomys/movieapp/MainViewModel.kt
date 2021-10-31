package dev.cynomys.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.cynomys.movieapp.movie.*

class MainViewModel constructor(private val repository: MovieRepository, page: Int) :
    ViewModel() {
    private var _movieList = MutableLiveData<Resource<List<Movie>>>()
    val movieList: LiveData<Resource<List<Movie>>>
        get() = _movieList

    init {
        getMovieList(page)
    }

    fun getMovieList(page: Int) {
        _movieList.value = Resource(NetworkStatus.LOADING)
        val response = object : NetworkResponse<List<Movie>> {
            override fun onResponse(value: Resource<List<Movie>>) {
                _movieList.value = value
            }
        }
        repository.getMovieList(response, page)
    }
}