package com.example.ejerciciobrubank.data.repository

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.data.source.SearchLocalSource
import com.example.ejerciciobrubank.data.source.SearchRemoteSource
import com.example.ejerciciobrubank.domain.repository.SearchRepository
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

class SearchRepositoryImpl(
    private val searchRemoteSource: SearchRemoteSource,
    private val searchLocalSource: SearchLocalSource
) : SearchRepository {

    override suspend fun getMoviesBySearch(query: String): Either<Constants.ApiError, List<Movie?>> {
        val localMovies = getLocalMovies(query)
        if (localMovies.isNotEmpty()) {
            return Either.Right(localMovies)
        }

        val eitherRemoteProducts = getRemoteMovies(query)

        if (eitherRemoteProducts is Either.Right) {
            addMoviesInDB(eitherRemoteProducts.r, query)
            val sortedList = eitherRemoteProducts.r.sortedBy { it?.title }
            Either.Right(sortedList)
        }

        return eitherRemoteProducts
    }

    override suspend fun getLocalMovies(query: String): List<Movie?> {
        return searchLocalSource.getMoviesBySearch(query)
    }

    override suspend fun getRemoteMovies(query: String): Either<Constants.ApiError, List<Movie?>> {
        return searchRemoteSource.getMoviesBySearch(query).value ?: Either.Left(Constants.ApiError.API_ERROR)
    }

    override suspend fun getMovie(idMovie: Long): Movie? {
        return searchLocalSource.getMovie(idMovie)
    }

    override suspend fun addMoviesInDB(result: List<Movie?>?, query: String) {
        result?.forEach { movie ->
            movie?.query = query
            searchLocalSource.insertMovies(movie)
        }
    }
}