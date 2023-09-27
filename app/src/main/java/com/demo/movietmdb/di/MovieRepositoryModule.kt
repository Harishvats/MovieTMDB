package com.demo.movietmdb.di

import com.demo.movietmdb.data.repository.MovieRepositoryImpl
import com.demo.movietmdb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {


    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

}