package com.demo.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieList(
    @SerialName("results")
    val movies: List<Movie>,
)
