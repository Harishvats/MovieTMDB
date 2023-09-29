package com.demo.movietmdb.data.mapper

import com.demo.movietmdb.data.TestData.movie
import com.demo.movietmdb.data.TestData.movieDTO
import com.demo.movietmdb.data.model.MovieListDTO
import com.demo.movietmdb.domain.model.MovieList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieListDtoToModelMapperTest {
    private lateinit var mapper: MovieListDtoToModelMapper

    @MockK
    private lateinit var movieDtoToModelMapper: MovieDtoToModelMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        mapper = MovieListDtoToModelMapper(movieDtoToModelMapper)
    }


    @Test
    fun `MovieListDtoToModelMapper maps MovieListDTO to MovieList model`() {
        val movieListDTO = MovieListDTO(listOf(movieDTO))
        val movieList = MovieList(listOf(movie))
        coEvery { movieDtoToModelMapper.mapFrom(any()) } returns (movie)
        val mappedMovieList = mapper.mapFrom(movieListDTO)
        Assert.assertEquals(
            mappedMovieList.movies[0].posterPath,
            movieList.movies[0].posterPath
        )
    }


}