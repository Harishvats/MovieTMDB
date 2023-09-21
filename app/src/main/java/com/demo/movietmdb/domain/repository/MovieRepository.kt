package com.demo.movietmdb.domain.repository

import com.demo.movietmdb.data.model.MovieDetails
import com.demo.movietmdb.data.model.MovieList
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(): Response<MovieList>
    suspend fun getMovieDetails(movieID: String): Response<MovieDetails>
}