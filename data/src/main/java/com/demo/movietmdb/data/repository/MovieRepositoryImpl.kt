package com.demo.movietmdb.data.repository

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.data.repository.datasource.MovieRemoteDataSource
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {
    override suspend fun getMovies(): Flow<ApiResponse<MovieList>> =
        movieRemoteDataSource.getMovies()

    override suspend fun getMovieDetails(movieID: Int): Flow<ApiResponse<MovieDetails>> =
        movieRemoteDataSource.getMovieDetails(movieId = movieID)
}