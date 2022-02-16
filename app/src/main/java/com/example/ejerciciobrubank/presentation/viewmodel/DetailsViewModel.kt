package com.example.ejerciciobrubank.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ejerciciobrubank.data.model.Movie
import com.example.ejerciciobrubank.domain.usecase.GetMovieUseCase
import kotlinx.coroutines.launch


/**
 * @author Axel Sanchez
 */
class DetailsViewModel(private val getMovieUseCase: GetMovieUseCase): ViewModel() {

    private val listData: MutableLiveData<Movie?> by lazy {
        MutableLiveData<Movie?>()
    }

    private fun setListData(result: Movie?) {
        listData.postValue(result)
    }

    fun getMovie(idMovie: Long) {
        viewModelScope.launch {
            setListData(getMovieUseCase.call(idMovie))
        }
    }

    fun getMovieLiveData(): LiveData<Movie?> {
        return listData
    }

    class MovieViewModelFactory(private val getMovieUseCase: GetMovieUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetMovieUseCase::class.java).newInstance(getMovieUseCase)
        }
    }
}