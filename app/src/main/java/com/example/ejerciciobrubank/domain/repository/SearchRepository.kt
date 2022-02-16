package com.example.ejerciciobrubank.domain.repository

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

interface SearchRepository {
    suspend fun getMoviesBySearch(query: String): Either<Constants.ApiError, List<Movie?>>
    suspend fun getLocalMovies(query: String): List<Movie?>
    suspend fun getRemoteMovies(query: String): Either<Constants.ApiError, List<Movie?>>
    suspend fun getMovie(idMovie: Long): Movie?
    suspend fun addMoviesInDB(result: List<Movie?>?, query: String)
}