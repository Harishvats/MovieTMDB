package com.demo.movietmdb.data.api

import com.demo.movietmdb.data.model.MovieDetails
import com.demo.movietmdb.data.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apikey: String): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieID: String, @Query("api_key") apikey: String): Response<MovieDetails>

}