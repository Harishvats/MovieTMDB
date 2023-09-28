package com.demo.movietmdb.data.mapper

import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.domain.model.MovieDetails
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDetailsDtoToModelMapperTest {

    private lateinit var mapper: MovieDetailsDtoToModelMapper

    @Before
    fun setup() {
        mapper = MovieDetailsDtoToModelMapper()
    }

    @Test
    fun `MovieDetailsDTO maps to MovieDetails`() {
        val movieDetailsDto = createFakeMovieDetailsDTO()
        val movieDetails = mapper.mapFrom(movieDetailsDto)
        Assert.assertEquals(movieDetails.runtime, createFakeMovieDetails().runtime)
    }

    private fun createFakeMovieDetailsDTO() = MovieDetailsDTO(
        "",
        123,
        "Overview",
        "posterpath1",
        "2023-07-26",
        122,
        "tagline",
        "Movie 1"
    )

    private fun createFakeMovieDetails() = MovieDetails(
        123,
        "Overview",
        "posterpath1",
        "tagline",
        "2023-07-26",
        "122 min",
        "Movie 1",
        ""
    )

}
