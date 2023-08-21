package com.example.frogsocial.Movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class OMDbResponse(
    @SerializedName("Search") val movies: List<Movies>,
    // other response fields...
)

data class Movies(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Country") val imdbRating: String,
    @SerializedName("Poster") val image: String,
    // other movie fields...
)

sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}