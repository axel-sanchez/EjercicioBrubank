package com.example.ejerciciobrubank.data.source

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.data.room.MovieDao

/**
 * @author Axel Sanchez
 */
interface SearchLocalSource {
    suspend fun getMoviesBySearch(query: String): List<Movie?>
    suspend fun getMovie(idMovie: Long): Movie?
    suspend fun insertMovies(movie: Movie?): Long
}

class SearchLocalSourceImpl(private val database: MovieDao): SearchLocalSource{
    override suspend fun getMoviesBySearch(query: String): List<Movie?> {
        return database.getMoviesBySearch(query)
    }

    override suspend fun getMovie(idMovie: Long): Movie? {
        return database.getMovie(idMovie)
    }

    override suspend fun insertMovies(movie: Movie?): Long {
        return database.insertMovie(movie)
    }
}