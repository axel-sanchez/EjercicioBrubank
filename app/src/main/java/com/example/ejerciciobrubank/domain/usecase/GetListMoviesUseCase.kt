package com.example.ejerciciobrubank.domain.usecase

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.domain.repository.MovieRepository
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

/**
 * @author Axel Sanchez
 */
interface GetListMoviesUseCase {
    suspend fun call(): Either<Constants.ApiError, List<Movie?>>
}

class GetListMoviesUseCaseImpl(private val repository: MovieRepository): GetListMoviesUseCase {
    override suspend fun call(): Either<Constants.ApiError, List<Movie?>> {
        return repository.getAllMovies()
    }
}