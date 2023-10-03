package com.demo.movietmdb.data.mapper

import com.demo.movietmdb.data.TestData
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
    fun `MovieDetailsDTOToModelMapper maps input MovieDetailsDTO to MovieDetails domain model`() {
        val mappedMovieDetails = mapper.mapFrom(TestData.movieDetailsDTO)

        Assert.assertEquals(mappedMovieDetails.runtime, TestData.movieDetails.runtime)
    }


}
