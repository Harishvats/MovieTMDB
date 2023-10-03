package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.domain.repository.MovieRepository
import com.demo.movietmdb.domain.usecase.TestData.errorMsg
import com.demo.movietmdb.domain.usecase.TestData.id
import com.demo.movietmdb.domain.usecase.TestData.movieDetails
import com.demo.movietmdb.domain.usecase.TestData.title
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

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        getMovieDetailsUseCase = GetMovieDetailsUseCase(mockMovieRepository)
    }

    @Test
    fun `getMovieDetailsUseCase on Success result from repository returns movie details as Success APiResponse`() =
        runTest {
            val movieId = id
            val title = title
            val expectedResponse = Response.Success(movieDetails)
            coEvery { (mockMovieRepository.getMovieDetails(movieId)) } returns (flow {
                emit(
                    expectedResponse
                )
            })

            val result = getMovieDetailsUseCase(movieId)

            result.collect { response ->
                assert(response is Response.Success)
                val data = (response as Response.Success).data
                assert(data.id == movieId)
                assert(data.title == title)
            }
        }

    @Test
    fun `getMovieDetailsUseCase on Error from repository returns error message as Error APiResponse`() =
        runTest {
            val movieId = id
            val errorString = errorMsg
            val expectedResponse = Response.Error(errorString)
            coEvery { (mockMovieRepository.getMovieDetails(movieId)) } returns (flow {
                emit(
                    expectedResponse
                )
            })

            val result = getMovieDetailsUseCase(movieId)

            result.collect { response ->
                assert(response is Response.Error)
                val errorMsg = (response as Response.Error).message
                Assert.assertEquals(errorMsg, errorString)
            }
        }
}