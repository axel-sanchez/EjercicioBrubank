package com.example.ejerciciobrubank.data.repository

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.data.source.MovieLocalSource
import com.example.ejerciciobrubank.data.source.MovieRemoteSource
import com.example.ejerciciobrubank.domain.repository.MovieRepository
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

class MovieRepositoryImpl(
        private val movieRemoteSource: MovieRemoteSource,
        private val movieLocalSource: MovieLocalSource
): MovieRepository {
    override suspend fun getAllMovies(): Either<Constants.ApiError, List<Movie?>> {

        val localMovies = getLocalMovies()
        if (localMovies.isNotEmpty()) {
            return Either.Right(localMovies)
        }

        val eitherRemoteProducts = getRemoteMovies()

        if (eitherRemoteProducts is Either.Right) {
            addMoviesInDB(eitherRemoteProducts.r)
            val sortedList = eitherRemoteProducts.r.sortedBy { it?.title }
            Either.Right(sortedList)
        }

        return eitherRemoteProducts
    }

    override suspend fun getLocalMovies(): List<Movie?> {
        return movieLocalSource.getAllMovies()
    }

    override suspend fun getRemoteMovies(): Either<Constants.ApiError, List<Movie?>> {
        return movieRemoteSource.getMovies().value ?: Either.Left(Constants.ApiError.API_ERROR)
    }

    override suspend fun getMovie(idMovie: Long): Movie? {
        return movieLocalSource.getMovie(idMovie)
    }

    override suspend fun addMoviesInDB(result: List<Movie?>?) {
        result?.forEach { movie ->
            movie?.id = movieLocalSource.insertMovies(movie)
        }
    }
}