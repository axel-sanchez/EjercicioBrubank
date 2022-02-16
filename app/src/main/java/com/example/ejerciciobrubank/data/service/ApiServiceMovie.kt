package com.example.ejerciciobrubank.data.service

import com.example.ejerciciobrubank.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceMovie {
    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Response<Result>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<Result>
}