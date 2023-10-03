package com.demo.movietmdb.data.mapper

import com.demo.movietmdb.data.TestData.movie
import com.demo.movietmdb.data.TestData.movieDTO
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
        val mappedMovie = mapper.mapFrom(movieDTO)

        Assert.assertEquals(mappedMovie.title, movie.title)
    }



}