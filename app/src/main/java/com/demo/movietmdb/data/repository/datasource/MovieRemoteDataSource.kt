package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun getMovies(): Flow<ApiResponse<MovieList>>
    suspend fun getMovieDetails(movieId: String): Flow<ApiResponse<MovieDetailsDTO>>
}