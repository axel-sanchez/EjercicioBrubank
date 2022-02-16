package com.example.ejerciciobrubank.helpers

import com.example.ejerciciobrubank.data.model.Movie

object DummyMovies {
    val movie1 = Movie(
        1,
        false, "", listOf(), "","","", 0.0, "", "",
        "Avengers 3",
        false, 0.0, 0, "", null, "", ""
    )

    val movie2 = Movie(
        2,
        false, "", listOf(), "","","", 0.0, "", "",
        "Spider Man: second",
        false, 0.0, 0, "", null, "", ""
    )

    val movie3 = Movie(
        3,
        false, "", listOf(), "","","", 0.0, "", "",
        "Avengers 1",
        false, 0.0, 0, "", null, "", ""
    )

    val movie4 = Movie(
        4,
        false, "", listOf(), "","","", 0.0, "", "",
        "Spider Man: first",
        false, 0.0, 0, "", null, "", ""
    )

    fun getListMovies(): Either<Constants.ApiError, List<Movie?>> {
        val listProducts = arrayListOf<Movie?>(movie1, movie2, movie3, movie4)
        return Either.Right(listProducts.toList())
    }
}