package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun getMovies(): Flow<Response<MovieList>>
    suspend fun getMovieDetails(movieId: Int): Flow<Response<MovieDetails>>
}