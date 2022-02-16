package com.example.ejerciciobrubank.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.domain.usecase.GetListMoviesUseCase
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class MoviesViewModel(private val getListMoviesUseCase: GetListMoviesUseCase): ViewModel() {

    private val listData: MutableLiveData<Either<Constants.ApiError, List<Movie?>>> by lazy {
        MutableLiveData<Either<Constants.ApiError, List<Movie?>>>().also {
            getMovies()
        }
    }

    private fun setListData(result: Either<Constants.ApiError, List<Movie?>>) {
        listData.postValue(result)
    }

    fun getMovies() {
        viewModelScope.launch {
            setListData(getListMoviesUseCase.call())
        }
    }

    fun getMovieLiveData(): LiveData<Either<Constants.ApiError, List<Movie?>>> {
        return listData
    }

    class MovieViewModelFactory(private val getListMoviesUseCase: GetListMoviesUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetListMoviesUseCase::class.java).newInstance(getListMoviesUseCase)
        }
    }
}