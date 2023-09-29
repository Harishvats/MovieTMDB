package com.demo.tmdb.movies

import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieDetails

object TestData {
    private const val id = 12344
    private const val backdropPath = "path.jpeg"
    private const val posterPath = "poster.jpeg"
    private const val overview = "Overview of Movie 1"
    private const val title = "Movie 1"
    private const val releaseDate = "2022-10-09"
    private const val tagline = "Movie 1 Tag"
    private const val runtime = "120"
    const val errorMsg = "Internal Error"

    val movieDetails = MovieDetails(
        id,
        overview,
        posterPath,
        tagline,
        releaseDate,
        runtime,
        title,
        backdropPath
    )
    val movie = Movie(
        id,
        posterPath,
        releaseDate,
        title
    )
}