package com.demo.movietmdb.domain.model

data class MovieDetails(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val tagline: String,
    val releaseDate: String,
    val runtime: String,
    val title: String,
    val backdropPath: String,
    )
