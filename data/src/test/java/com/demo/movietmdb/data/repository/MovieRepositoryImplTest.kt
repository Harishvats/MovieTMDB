package com.demo.movietmdb.data.repository

import com.demo.movietmdb.common.ApiResponse
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
            val expectedApiResponse =
                ApiResponse.Success(MovieList(emptyList()))
            coEvery { (mockMovieRemoteDataSource.getMovies()) } returns (flow {
                emit(
                    expectedApiResponse
                )
            })

            // Act
            val resultFlow: Flow<ApiResponse<MovieList>> =
                movieRepository.getMovies()

            // Assert
            val resultList: List<ApiResponse<MovieList>> = resultFlow.toList()
            assertEquals(listOf(expectedApiResponse), resultList)
        }

    @Test
    fun `getMovieDetails on success returns movie details as ApiResponse`() = runTest {
        // Arrange
        val movieId = 123
        val expectedApiResponse = ApiResponse.Success(
            MovieDetails(
                123,
                "Overview",
                "posterpath1",
                "tagline",
                "2023-07-26",
                "122",
                "Movie 1",
                ""
            )
        )
        coEvery { (mockMovieRemoteDataSource.getMovieDetails(movieId)) } returns (flow {
            emit(
                expectedApiResponse
            )
        })

        // Act
        val resultFlow: Flow<ApiResponse<MovieDetails>> =
            movieRepository.getMovieDetails(movieId)

        // Assert
        val result: ApiResponse<MovieDetails> = resultFlow.last()
        assertEquals(expectedApiResponse, result)
    }
}