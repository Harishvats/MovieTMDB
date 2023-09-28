package com.demo.movietmdb.data.repository

import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MovieRepositoryImplTest {
    @Mock
    private lateinit var mockMovieRemoteDataSource: com.demo.movietmdb.data.repository.datasource.MovieRemoteDataSource

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieRepository =
            MovieRepositoryImpl(mockMovieRemoteDataSource)
    }

    @Test
    fun `test getMovies success`() = runTest {
        // Arrange
        val expectedApiResponse = com.demo.movietmdb.common.ApiResponse.Success(MovieList(emptyList()))
        `when`(mockMovieRemoteDataSource.getMovies()).thenReturn(flow { emit(expectedApiResponse) })

        // Act
        val resultFlow: Flow<com.demo.movietmdb.common.ApiResponse<MovieList>> = movieRepository.getMovies()

        // Assert
        val resultList: List<com.demo.movietmdb.common.ApiResponse<MovieList>> = resultFlow.toList()
        assertEquals(listOf(expectedApiResponse), resultList)
    }

    @Test
    fun `test getMovieDetails success`() = runTest {
        // Arrange
        val movieId = 123
        val expectedApiResponse = com.demo.movietmdb.common.ApiResponse.Success(
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
        `when`(mockMovieRemoteDataSource.getMovieDetails(movieId)).thenReturn(flow {
            emit(
                expectedApiResponse
            )
        })

        // Act
        val resultFlow: Flow<com.demo.movietmdb.common.ApiResponse<MovieDetails>> = movieRepository.getMovieDetails(movieId)

        // Assert
        val result: com.demo.movietmdb.common.ApiResponse<MovieDetails> = resultFlow.last()
        assertEquals(expectedApiResponse, result)
    }
}