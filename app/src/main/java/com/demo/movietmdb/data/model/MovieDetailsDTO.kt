package com.demo.movietmdb.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDTO(
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("id")
    val id: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("title")
    val title: String,
)