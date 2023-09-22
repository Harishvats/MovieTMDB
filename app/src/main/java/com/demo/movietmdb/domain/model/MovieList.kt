package com.demo.movietmdb.domain.model


data class MovieList(
    val movies: List<Movie>,
)

data class Movie(
    val id: Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)
