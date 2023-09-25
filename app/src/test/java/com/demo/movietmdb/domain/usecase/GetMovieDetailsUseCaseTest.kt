package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetMovieDetailsUseCaseTest {
    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getMovieDetailsUseCase = GetMovieDetailsUseCase(mockMovieRepository)
    }

    @Test
    fun `test execute`() = runTest {
        val movieDetails = MovieDetails(
            id = 12344,
            backdropPath = "",
            posterPath = "",
            overview = "Overview of Movie 1",
            title = "Movie 1",
            releaseDate = "2022-10-09",
            tagline = "Movie 1 Tag",
            runtime = 120
        )
        // Arrange
        val movieId = 12344
        val expectedResponse = ApiResponse.Success(movieDetails)
        `when`(mockMovieRepository.getMovieDetails(movieId)).thenReturn(flow { emit(expectedResponse) })

        // Act
        val result = getMovieDetailsUseCase.execute(movieId)

        // Assert
        result.collect { response ->
            assert(response is ApiResponse.Success)
            val data = (response as ApiResponse.Success).data
            assert(data.id == movieId)
            assert(data.title == "Movie 1")
        }
    }
}