package com.example.ejerciciobrubank.di

import androidx.room.Room
import com.example.ejerciciobrubank.data.repository.MovieRepositoryImpl
import com.example.ejerciciobrubank.data.repository.SearchRepositoryImpl
import com.example.ejerciciobrubank.data.room.Database
import com.example.ejerciciobrubank.data.service.ApiClient
import com.example.ejerciciobrubank.data.service.ApiServiceMovie
import com.example.ejerciciobrubank.data.source.*
import com.example.ejerciciobrubank.domain.repository.MovieRepository
import com.example.ejerciciobrubank.domain.repository.SearchRepository
import com.example.ejerciciobrubank.domain.usecase.*
import com.example.ejerciciobrubank.helpers.Constants.BASE_URL
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val movieModule = module {
    single {
        ApiClient.Builder<ApiServiceMovie>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServiceMovie::class.java)
            .build()
    }

    //Movie
    single<MovieRepository> {
        MovieRepositoryImpl(
            get() as MovieRemoteSource,
            get() as MovieLocalSource
        )
    }
    single<MovieRemoteSource> { MovieRemoteSourceImpl(get() as ApiServiceMovie) }
    single<MovieLocalSource> { MovieLocalSourceImpl((get() as Database).movieDao()) }
    single {
        Room
            .databaseBuilder(androidContext(), Database::class.java, "BrubankDB.db3")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<GetListMoviesUseCase> { GetListMoviesUseCaseImpl(get() as MovieRepository) }

    //Search
    single<SearchRemoteSource> { SearchRemoteSourceImpl(get() as ApiServiceMovie) }
    single<SearchLocalSource> { SearchLocalSourceImpl((get() as Database).movieDao()) }

    single<SearchRepository> {
        SearchRepositoryImpl(
            get() as SearchRemoteSource,
            get() as SearchLocalSource
        )
    }

    single<GetMoviesBySearchUseCase> { GetMoviesBySearchUseCaseImpl(get() as SearchRepository) }

    single<GetMovieUseCase> { GetMovieUseCaseImpl(get() as MovieRepository) }
}