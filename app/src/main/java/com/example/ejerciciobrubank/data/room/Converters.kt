package com.example.ejerciciobrubank.data.room

import android.widget.ImageView
import androidx.room.TypeConverter
import com.example.ejerciciobrubank.data.model.Movie
import com.google.gson.Gson

/**
 * @author Axel Sanchez
 */
class Converters{
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromMovie(movie: Movie?): String? {
        movie?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toMovie(movieString: String?): Movie? {
        movieString?.let {
            return gson.fromJson(it, Movie::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromImageView(imageView: ImageView?): String? {
        imageView?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toImageView(imageViewString: String?): ImageView? {
        imageViewString?.let {
            return gson.fromJson(it, ImageView::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromGenreIds(genreIds: List<Int?>?): String? {
        var response = ""
        genreIds?.let {
            for (i in genreIds.indices) {
                response += if (i == 0) genreIds[i]
                else ";${genreIds[i]}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toGenreIds(concat: String?): List<Int?>? {
        if(concat?.isEmpty() != false) return null
        val list = concat.split(";")
        list.let {
            return it.map { str -> if (str != "null") str.toInt() else null }
        } ?: return null
    }
}