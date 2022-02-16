package com.example.ejerciciobrubank.data.source

import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.data.room.MovieDao

/**
 * @author Axel Sanchez
 */
interface MovieLocalSource {
    suspend fun getAllMovies(): List<Movie?>
    suspend fun getMovie(idMovie: Long): Movie?
    suspend fun insertMovies(movie: Movie?): Long
}

class MovieLocalSourceImpl(private val database: MovieDao): MovieLocalSource{
    override suspend fun getAllMovies(): List<Movie?> {
        return database.getAllMovies()
    }

    override suspend fun getMovie(idMovie: Long): Movie? {
        return database.getMovie(idMovie)
    }

    override suspend fun insertMovies(movie: Movie?): Long {
        return database.insertMovie(movie)
    }
}