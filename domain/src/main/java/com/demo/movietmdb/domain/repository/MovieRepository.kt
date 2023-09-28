package com.demo.movietmdb.domain.repository

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<ApiResponse<MovieList>>
    suspend fun getMovieDetails(movieID: Int): Flow<ApiResponse<MovieDetails>>
}