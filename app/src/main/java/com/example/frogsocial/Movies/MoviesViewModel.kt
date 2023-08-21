package com.example.frogsocial.Movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val mainRepository: MoviesRepository
) : ViewModel() {

    private var _movieResponse = MutableLiveData<NetworkResult<List<Movies>>>()
    val movieResponse: LiveData<NetworkResult<List<Movies>>> = _movieResponse

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            mainRepository.getMovies().collect {
                _movieResponse.postValue(it)
            }
        }
    }
}