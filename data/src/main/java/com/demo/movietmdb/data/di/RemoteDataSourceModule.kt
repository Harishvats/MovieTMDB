package com.demo.movietmdb.data.di

import com.demo.movietmdb.data.repository.datasource.MovieRemoteDataSource
import com.demo.movietmdb.data.repository.datasource.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(remoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}