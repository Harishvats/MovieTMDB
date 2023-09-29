package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {
    @MockK
    private lateinit var mockMovieRepository: MovieRepository

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetails: MovieDetails

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
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
    fun `getMovieDetailsUseCase on Success result from repository returns movie details as Success APiResponse`() =
        runTest {
            val movieId = 12344
            val expectedResponse = ApiResponse.Success(movieDetails)
            coEvery { (mockMovieRepository.getMovieDetails(movieId)) } returns (flow {
                emit(
                    expectedResponse
                )
            })

            val result = getMovieDetailsUseCase(movieId)

            result.collect { response ->
                assert(response is ApiResponse.Success)
                val data = (response as ApiResponse.Success).data
                assert(data.id == movieId)
                assert(data.title == "Movie 1")
            }
        }

    @Test
    fun `getMovieDetailsUseCase on Error from repository returns error message as Error APiResponse`() =
        runTest {
            val movieId = 12344
            val errorString = "Invalid Movie ID"
            val expectedResponse = ApiResponse.Error(errorString)
            coEvery { (mockMovieRepository.getMovieDetails(movieId)) } returns (flow {
                emit(
                    expectedResponse
                )
            })

            val result = getMovieDetailsUseCase(movieId)

            result.collect { response ->
                assert(response is ApiResponse.Error)
                val errorMsg = (response as ApiResponse.Error).message
                Assert.assertEquals(errorMsg, errorString)
            }
        }
}