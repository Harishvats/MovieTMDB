package com.demo.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListDTO(
    @SerialName("results")
    val movieDTO: List<MovieDTO>,
)

@Serializable
data class MovieDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("title")
    val title: String,
)
