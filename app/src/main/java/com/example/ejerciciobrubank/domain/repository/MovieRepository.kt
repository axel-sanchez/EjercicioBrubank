package com.example.ejerciciobrubank.domain.repository

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

interface MovieRepository {
    suspend fun getAllMovies(): Either<Constants.ApiError, List<Movie?>>
    suspend fun getLocalMovies(): List<Movie?>
    suspend fun getRemoteMovies(): Either<Constants.ApiError, List<Movie?>>
    suspend fun getMovie(idMovie: Long): Movie?
    suspend fun addMoviesInDB(result: List<Movie?>?)
}