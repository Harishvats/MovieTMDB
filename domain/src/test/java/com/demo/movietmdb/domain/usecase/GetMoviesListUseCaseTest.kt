package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class GetMoviesListUseCaseTest {

    @MockK
    private lateinit var mockMovieRepository: MovieRepository

    private lateinit var getMoviesListUseCase: GetMoviesListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        getMoviesListUseCase = GetMoviesListUseCase(mockMovieRepository)
    }

    @Test
    fun `getMoviesListUseCase on success returns list of movies as Success ApiResponse`() = runTest {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "posterpath1", "2023-07-26", "Movie 1"))
        movies.add(
            Movie(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        movies.add(
            Movie(3, "posterpath3", "2022-08-06", "Movie 3")
        )

        val expectedResponse = ApiResponse.Success(MovieList(movies))
        coEvery { (mockMovieRepository.getMovies()) } returns (flow { emit(expectedResponse) })

        val result = getMoviesListUseCase()

        result.collect { response ->
            assert(response is ApiResponse.Success)
            val data = (response as ApiResponse.Success).data
            assert(data.movies.size == 3)
            assert(data.movies[0].title == "Movie 1")
        }
    }

    @Test
    fun `getMoviesListUseCase on error returns error message as Error ApiResponse`() = runTest {
        val errorString = "Internal Server Error"
        val expectedResponse = ApiResponse.Error(errorString)
        coEvery { (mockMovieRepository.getMovies()) } returns (flow { emit(expectedResponse) })

        val result = getMoviesListUseCase()

        result.collect { response ->
            assert(response is ApiResponse.Error)
            val errorMsg = (response as ApiResponse.Error).message
            Assert.assertEquals(errorMsg, errorString)
        }
    }
}