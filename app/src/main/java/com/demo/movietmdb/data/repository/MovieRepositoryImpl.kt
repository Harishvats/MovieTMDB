package com.demo.movietmdb.data.repository

import com.demo.movietmdb.data.repository.datasource.MovieRemoteDataSource
import com.demo.movietmdb.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {
    override suspend fun getMovies() = movieRemoteDataSource.getMovies()

    override suspend fun getMovieDetails(movieID: String) =
        movieRemoteDataSource.getMovieDetails(movieId = movieID)
}