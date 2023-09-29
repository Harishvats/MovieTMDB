package com.demo.movietmdb.data.mapper

import com.demo.movietmdb.data.model.MovieDTO
import com.demo.movietmdb.domain.model.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDtoToModelMapperTest {
    private lateinit var mapper: MovieDtoToModelMapper

    @Before
    fun setup() {
        mapper = MovieDtoToModelMapper()
    }


    @Test
    fun `MovieDtoToModelMapper maps MovieListDTO to MovieList model`() {
        val movieDTO = createFakeMovieDTO()
        val movie = mapper.mapFrom(movieDTO)
        Assert.assertEquals(movie.title, createFakeMovie().title)
    }

    private fun createFakeMovieDTO(): MovieDTO = MovieDTO(
        1,
        "posterpath1",
        "2023-07-26",
        "Movie 1"
    )

    private fun createFakeMovie(): Movie =
        Movie(1, "posterpath1", "2023-07-26", "Movie 1")


}