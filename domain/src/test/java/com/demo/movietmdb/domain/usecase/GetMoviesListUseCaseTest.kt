package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import com.demo.movietmdb.domain.usecase.TestData.errorMsg
import com.demo.movietmdb.domain.usecase.TestData.movie
import com.demo.movietmdb.domain.usecase.TestData.title
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
    fun `getMoviesListUseCase on success returns list of movies as Success ApiResponse`() =
        runTest {
            val movies = listOf(movie)
            val expectedResponse = Response.Success(MovieList(movies))
            coEvery { (mockMovieRepository.getMovies()) } returns (flow { emit(expectedResponse) })

            val result = getMoviesListUseCase()

            result.collect { response ->
                assert(response is Response.Success)
                val data = (response as Response.Success).data
                assert(data.movies.size == 1)
                assert(data.movies[0].title == title)
            }
        }

    @Test
    fun `getMoviesListUseCase on error returns error message as Error ApiResponse`() = runTest {
        val errorString = errorMsg
        val expectedResponse = Response.Error(errorString)
        coEvery { (mockMovieRepository.getMovies()) } returns (flow { emit(expectedResponse) })

        val result = getMoviesListUseCase()

        result.collect { response ->
            assert(response is Response.Error)
            val errorMsg = (response as Response.Error).message
            Assert.assertEquals(errorMsg, errorString)
        }
    }
}