package com.example.ejerciciobrubank.domain.usecase

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.domain.repository.SearchRepository
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

/**
 * @author Axel Sanchez
 */
interface GetMoviesBySearchUseCase {
    suspend fun call(query: String): Either<Constants.ApiError, List<Movie?>>
}

class GetMoviesBySearchUseCaseImpl(private val repository: SearchRepository): GetMoviesBySearchUseCase {
    override suspend fun call(query: String): Either<Constants.ApiError, List<Movie?>> {
        return repository.getMoviesBySearch(query)
    }
}