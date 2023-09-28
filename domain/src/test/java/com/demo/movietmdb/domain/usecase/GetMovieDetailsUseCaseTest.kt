package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetMovieDetailsUseCaseTest {
    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetails: MovieDetails

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getMovieDetailsUseCase = GetMovieDetailsUseCase(mockMovieRepository)
        movieDetails = MovieDetails(
            id = 12344,
            backdropPath = "",
            posterPath = "",
            overview = "Overview of Movie 1",
            title = "Movie 1",
            releaseDate = "2022-10-09",
            tagline = "Movie 1 Tag",
            runtime = "120"
        )

    }

    @Test
    fun `fetch movie details successfully`() = runTest {
        val movieId = 12344
        val expectedResponse = com.demo.movietmdb.common.ApiResponse.Success(movieDetails)
        `when`(mockMovieRepository.getMovieDetails(movieId)).thenReturn(flow { emit(expectedResponse) })

        val result = getMovieDetailsUseCase(movieId)

        result.collect { response ->
            assert(response is com.demo.movietmdb.common.ApiResponse.Success)
            val data = (response as com.demo.movietmdb.common.ApiResponse.Success).data
            assert(data.id == movieId)
            assert(data.title == "Movie 1")
        }
    }

    @Test
    fun `error in fetching movie details`() = runTest {
        val movieId = 12344
        val errorString = "Invalid Movie ID"
        val expectedResponse = com.demo.movietmdb.common.ApiResponse.Error(errorString)
        `when`(mockMovieRepository.getMovieDetails(movieId)).thenReturn(flow { emit(expectedResponse) })

        val result = getMovieDetailsUseCase(movieId)

        result.collect { response ->
            assert(response is com.demo.movietmdb.common.ApiResponse.Error)
            val errorMsg = (response as com.demo.movietmdb.common.ApiResponse.Error).message
            Assert.assertEquals(errorMsg, errorString)
        }
    }
}