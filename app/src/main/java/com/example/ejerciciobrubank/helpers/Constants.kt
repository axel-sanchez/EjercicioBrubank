package com.example.ejerciciobrubank.helpers

/**
 * @author Axel Sanchez
 */
object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "208ca80d1e219453796a7f9792d16776"
    const val THERE_IS_NOT_MOVIES = "No se obtuvo ninguna película"
    const val THE_MOVIE_IS_NOT_EXISTS = "Error al obtener los detalles de la película"
    const val BASE_URL_IMAGEN = "https://image.tmdb.org/t/p/w500"

    enum class ApiError(var error: String) {
        API_ERROR("Error al obtener las peliculas")
    }

    enum class Genres(val id: Int, val nameGenre: String){
        ACTION(28, "Action"),
        ADVENTURE(12, "Adventure"),
        ANIMATION(16, "Animation"),
        COMEDY(35, "Comedy"),
        CRIME(80, "Crime"),
        DOCUMENTARY(99, "Documentary"),
        DRAMA(18, "Drama"),
        FAMILY(10751, "Family"),
        FANTASY(14, "Fantasy"),
        HISTORY(36, "History"),
        HORROR(27, "Horror"),
        MUSIC(10402, "Music"),
        MYSTERY(9648, "Mystery"),
        ROMANCE(10749, "Romance"),
        SCIENCE_FICTION(878, "Science Fiction"),
        TV_MOVIE(10770, "TV Movie"),
        THRILLER(53, "Thriller"),
        WAR(10752, "War"),
        WESTERN(37, "Western")
    }

    const val ID_MOVIE = "idMovie"
    const val SHARED_ELEMENTS_TRANSITION = "SharedElementsTransition"
}