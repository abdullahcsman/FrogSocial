package com.example.frogsocial.Movies

import com.example.frogsocial.Api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovies(): Flow<NetworkResult<List<Movies>>> = flow {
        emit(NetworkResult.Loading(true))
        val apiKey = "8cda1a19"
        val response = apiService.searchMovies(apiKey, "love")
        val filteredMovies = response.movies.filter { movie ->
            (movie.year.toIntOrNull() ?: 0) > 2000
        }
        val sortedMovies = filteredMovies.sortedBy { it.year }
        emit(NetworkResult.Success(sortedMovies))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}