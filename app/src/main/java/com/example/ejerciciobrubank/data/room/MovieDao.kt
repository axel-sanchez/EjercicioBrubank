package com.example.ejerciciobrubank.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ejerciciobrubank.data.model.Movie

/**
 * @author Axel Sanchez
 */
@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie WHERE `query` is null order by title")
    suspend fun getAllMovies(): List<Movie?>

    @Query("SELECT * FROM Movie where id = :idMovie")
    suspend fun getMovie(idMovie: Long): Movie?

    @Query("SELECT * FROM Movie WHERE `query` = :query")
    suspend fun getMoviesBySearch(query: String): List<Movie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie?): Long
}