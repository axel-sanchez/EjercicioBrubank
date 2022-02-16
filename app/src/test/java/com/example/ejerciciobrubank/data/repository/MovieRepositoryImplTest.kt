package com.example.ejerciciobrubank.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.ejerciciobrubank.data.source.MovieLocalSource
import com.example.ejerciciobrubank.data.source.MovieRemoteSource
import com.example.ejerciciobrubank.domain.repository.MovieRepository
import com.example.ejerciciobrubank.helpers.DummyMovies.getListMovies
import com.example.ejerciciobrubank.helpers.DummyMovies.movie1
import com.example.ejerciciobrubank.helpers.DummyMovies.movie2
import com.example.ejerciciobrubank.helpers.DummyMovies.movie3
import com.example.ejerciciobrubank.helpers.DummyMovies.movie4
import com.example.ejerciciobrubank.helpers.Either
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mockito

class MovieRepositoryImplTest {

    private val movieRemoteSource: MovieRemoteSource = Mockito.mock(MovieRemoteSource::class.java)
    private val movieLocalSource: MovieLocalSource = Mockito.mock(MovieLocalSource::class.java)
    private val movieRepository: MovieRepository = MovieRepositoryImpl(movieRemoteSource, movieLocalSource)

    @Test
    fun should_return_movie_list_sorted_by_title() {
        runBlocking {
            BDDMockito.given(movieRepository.getLocalMovies()).willReturn(listOf())

            val mutableListData = MutableLiveData(getListMovies())
            BDDMockito.given(movieRemoteSource.getMovies()).willReturn(mutableListData)

            BDDMockito.given(movieLocalSource.insertMovies(movie1)).willReturn(1)
            BDDMockito.given(movieLocalSource.insertMovies(movie2)).willReturn(2)
            BDDMockito.given(movieLocalSource.insertMovies(movie3)).willReturn(3)
            BDDMockito.given(movieLocalSource.insertMovies(movie4)).willReturn(4)

            val result = movieRepository.getAllMovies()
            MatcherAssert.assertThat((result as Either.Right).r, Matchers.contains(movie3, movie1, movie4, movie2))
        }
    }

    @Test
    fun should_calls_to_getRemoteMovies_when_there_are_not_local_movies(){
        runBlocking {
            val mutableListData = MutableLiveData(getListMovies())
            BDDMockito.given(movieRemoteSource.getMovies()).willReturn(mutableListData)

            BDDMockito.given(movieLocalSource.insertMovies(movie1)).willReturn(1)
            BDDMockito.given(movieLocalSource.insertMovies(movie2)).willReturn(2)
            BDDMockito.given(movieLocalSource.insertMovies(movie3)).willReturn(3)
            BDDMockito.given(movieLocalSource.insertMovies(movie4)).willReturn(4)

            BDDMockito.given(movieRepository.getLocalMovies()).willReturn(listOf())

            movieRepository.getAllMovies()
            Mockito.verify(movieRemoteSource).getMovies()
        }
    }

    @Test
    fun should_not_calls_to_getRemoteMovies_when_there_are_local_movies(){
        runBlocking {
            val mutableListData = MutableLiveData(getListMovies())
            BDDMockito.given(movieRemoteSource.getMovies()).willReturn(mutableListData)

            BDDMockito.given(movieLocalSource.insertMovies(movie1)).willReturn(1)
            BDDMockito.given(movieLocalSource.insertMovies(movie2)).willReturn(2)
            BDDMockito.given(movieLocalSource.insertMovies(movie3)).willReturn(3)
            BDDMockito.given(movieLocalSource.insertMovies(movie4)).willReturn(4)

            BDDMockito.given(movieRepository.getLocalMovies()).willReturn(listOf(movie1))

            movieRepository.getAllMovies()
            Mockito.verify(movieRemoteSource, BDDMockito.never()).getMovies()
        }
    }
}