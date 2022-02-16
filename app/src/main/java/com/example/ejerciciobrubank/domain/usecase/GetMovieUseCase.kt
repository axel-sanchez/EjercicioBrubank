package com.example.ejerciciobrubank.domain.usecase

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.domain.repository.MovieRepository

/**
 * @author Axel Sanchez
 */
interface GetMovieUseCase{
    suspend fun call(idMovie: Long): Movie?
}

class GetMovieUseCaseImpl(private val repository: MovieRepository): GetMovieUseCase {
    override suspend fun call(idMovie: Long): Movie? {
        return repository.getMovie(idMovie)
    }
}