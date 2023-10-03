package com.demo.movietmdb.domain.repository

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<Response<MovieList>>
    suspend fun getMovieDetails(movieID: Int): Flow<Response<MovieDetails>>
}