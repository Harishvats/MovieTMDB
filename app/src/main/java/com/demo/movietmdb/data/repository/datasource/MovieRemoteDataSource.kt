package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.data.model.MovieDetails
import com.demo.movietmdb.data.model.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieList>
    suspend fun getMovieDetails(movieId: String): Response<MovieDetails>
}