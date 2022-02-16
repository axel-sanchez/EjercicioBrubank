package com.example.ejerciciobrubank.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.data.service.ApiServiceMovie
import com.example.ejerciciobrubank.helpers.Constants.API_KEY
import com.example.ejerciciobrubank.helpers.Constants.ApiError
import com.example.ejerciciobrubank.helpers.Constants.ApiError.API_ERROR
import com.example.ejerciciobrubank.helpers.Either

/**
 * @author Axel Sanchez
 */
interface MovieRemoteSource {
    suspend fun getMovies(): MutableLiveData<Either<ApiError, List<Movie?>>>
}

class MovieRemoteSourceImpl(private val service: ApiServiceMovie) : MovieRemoteSource {
    override suspend fun getMovies(): MutableLiveData<Either<ApiError, List<Movie?>>> {
        val mutableLiveData = MutableLiveData<Either<ApiError, List<Movie?>>>()

        try {
            val response = service.getPopularMovies(API_KEY)
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())
                Log.i("Url Response", response.raw().networkResponse()?.request()?.url().toString())
                response.body()?.let { movies ->
                    movies.results.let{ results ->
                        mutableLiveData.value = Either.Right(results)
                    }
                } ?: kotlin.run {
                    mutableLiveData.value = Either.Left(API_ERROR)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = API_ERROR
                apiError.error = response.message()
                mutableLiveData.value = Either.Left(apiError)
            }
        } catch (e: Exception) {
            mutableLiveData.value = Either.Left(API_ERROR)
            Log.e(
                "MovieRemoteSourceImpl",
                e.message?:"Error al obtener las peliculas"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}