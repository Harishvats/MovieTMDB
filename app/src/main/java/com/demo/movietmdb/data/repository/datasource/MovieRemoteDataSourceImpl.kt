package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.data.api.TMDBService
import com.demo.movietmdb.data.model.MovieDetails
import com.demo.movietmdb.data.model.MovieList
import retrofit2.Response

class MovieRemoteDataSourceImpl(private val tmdbService: TMDBService, private val apiKey: String) :
    MovieRemoteDataSource {

    override suspend fun getMovies(): Response<MovieList> = tmdbService.getMovies(apiKey)


    override suspend fun getMovieDetails(movieId: String): Response<MovieDetails> =
        tmdbService.getMovieDetails(movieId, apiKey)

}