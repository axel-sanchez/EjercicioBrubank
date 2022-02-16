package com.example.ejerciciobrubank.data.model

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) var id: Long,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    var origen: String? = null,
    var imageView: ImageView? = null,
    var keyVideo: String? = null,
    var query: String? = null
)
