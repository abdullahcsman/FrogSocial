package com.example.frogsocial.Api

import com.example.frogsocial.Movies.OMDbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String,
    ): OMDbResponse
}