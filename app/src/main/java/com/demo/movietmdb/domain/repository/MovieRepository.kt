package com.demo.movietmdb.domain.repository

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<ApiResponse<MovieList>>
    suspend fun getMovieDetails(movieID: String): Flow<ApiResponse<MovieDetailsDTO>>
}