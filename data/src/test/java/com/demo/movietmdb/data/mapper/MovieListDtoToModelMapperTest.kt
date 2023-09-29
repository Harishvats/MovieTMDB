package com.demo.movietmdb.data.mapper

import com.demo.movietmdb.data.model.MovieDTO
import com.demo.movietmdb.data.model.MovieListDTO
import com.demo.movietmdb.domain.model.Movie
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
        val movieListDTO = createFakeMovieListDTO()
        coEvery { movieDtoToModelMapper.mapFrom(any()) } returns (Movie(1, "posterpath1", "", ""))
        val movieList = mapper.mapFrom(movieListDTO)
        Assert.assertEquals(
            movieList.movies[0].posterPath,
            createFakeMovieList().movies[0].posterPath
        )
    }

    private fun createFakeMovieListDTO(): MovieListDTO {
        val moviesDTO = mutableListOf<MovieDTO>()
        moviesDTO.add(
            MovieDTO(
                1,
                "posterpath1",
                "2023-07-26",
                "Movie 1"
            )
        )
        moviesDTO.add(
            MovieDTO(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        moviesDTO.add(
            MovieDTO(3, "posterpath3", "2022-08-06", "Movie 3")
        )
        return MovieListDTO(moviesDTO)

    }

    private fun createFakeMovieList(): MovieList {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "posterpath1", "2023-07-26", "Movie 1"))
        movies.add(
            Movie(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        movies.add(
            Movie(3, "posterpath3", "2022-08-06", "Movie 3")
        )
        return MovieList(movies)
    }
}