package com.demo.movietmdb.data.repository

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.data.TestData.id
import com.demo.movietmdb.data.TestData.movieDetails
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    @MockK
    private lateinit var mockMovieRemoteDataSource: com.demo.movietmdb.data.repository.datasource.MovieRemoteDataSource

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        movieRepository =
            MovieRepositoryImpl(mockMovieRemoteDataSource)
    }

    @Test
    fun `getMovies on getting result from MovieRemoteDataSource returns movie list as flow of Success ApiResponse`() =
        runTest {
            // Arrange
            val expectedResponse =
                Response.Success(MovieList(emptyList()))
            coEvery { (mockMovieRemoteDataSource.getMovies()) } returns (flow {
                emit(
                    expectedResponse
                )
            })

            // Act
            val resultFlow: Flow<Response<MovieList>> =
                movieRepository.getMovies()

            // Assert
            val resultList: List<Response<MovieList>> = resultFlow.toList()
            assertEquals(listOf(expectedResponse), resultList)
        }

    @Test
    fun `getMovieDetails on success returns movie details as ApiResponse`() = runTest {
        // Arrange
        val movieId = id
        val expectedResponse = Response.Success(
            movieDetails
        )
        coEvery { (mockMovieRemoteDataSource.getMovieDetails(movieId)) } returns (flow {
            emit(
                expectedResponse
            )
        })

        // Act
        val resultFlow: Flow<Response<MovieDetails>> =
            movieRepository.getMovieDetails(movieId)

        // Assert
        val result: Response<MovieDetails> = resultFlow.last()
        assertEquals(expectedResponse, result)
    }
}