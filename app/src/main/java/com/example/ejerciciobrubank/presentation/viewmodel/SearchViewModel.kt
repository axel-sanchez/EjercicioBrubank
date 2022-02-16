package com.example.ejerciciobrubank.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.domain.usecase.GetMoviesBySearchUseCase
import com.example.ejerciciobrubank.helpers.Constants
import com.example.ejerciciobrubank.helpers.Either
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class SearchViewModel(private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase): ViewModel() {

    private val listData: MutableLiveData<Either<Constants.ApiError, List<Movie?>>> by lazy {
        MutableLiveData<Either<Constants.ApiError, List<Movie?>>>()
    }

    private fun setListData(result: Either<Constants.ApiError, List<Movie?>>) {
        listData.postValue(result)
    }

    fun getMoviesBySearch(query: String) {
        viewModelScope.launch {
            setListData(getMoviesBySearchUseCase.call(query))
        }
    }

    fun getMoviesLiveData(): LiveData<Either<Constants.ApiError, List<Movie?>>> {
        return listData
    }

    class MoviesBySearchViewModelFactory(private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetMoviesBySearchUseCase::class.java).newInstance(getMoviesBySearchUseCase)
        }
    }
}