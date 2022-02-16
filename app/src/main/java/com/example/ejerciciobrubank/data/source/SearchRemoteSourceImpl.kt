package com.example.ejerciciobrubank.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.data.service.ApiServiceMovie
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either

/**
 * @author Axel Sanchez
 */
interface SearchRemoteSource {
    suspend fun getMoviesBySearch(query: String): MutableLiveData<Either<Constants.ApiError, List<Movie?>>>
}

class SearchRemoteSourceImpl(private val service: ApiServiceMovie) : SearchRemoteSource {
    override suspend fun getMoviesBySearch(query: String): MutableLiveData<Either<Constants.ApiError, List<Movie?>>> {
        val mutableLiveData = MutableLiveData<Either<Constants.ApiError, List<Movie?>>>()

        try {
            val response = service.searchMovies(Constants.API_KEY, query)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())
                Log.i("Url Response", response.raw().networkResponse()?.request()?.url().toString())
                response.body()?.let { movies ->
                    movies.results.let{ results ->
                        mutableLiveData.value = Either.Right(results)
                    }
                } ?: kotlin.run {
                    mutableLiveData.value = Either.Left(Constants.ApiError.API_ERROR)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = Constants.ApiError.API_ERROR
                apiError.error = response.message()
                mutableLiveData.value = Either.Left(apiError)
            }
        } catch (e: Exception) {
            mutableLiveData.value = Either.Left(Constants.ApiError.API_ERROR)
            Log.e(
                "MovieRemoteSourceImpl",
                e.message?:"Error al obtener las peliculas"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}