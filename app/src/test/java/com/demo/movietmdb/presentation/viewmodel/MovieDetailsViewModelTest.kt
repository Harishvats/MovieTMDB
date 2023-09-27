package com.demo.movietmdb.presentation.viewmodel

import com.demo.movietmdb.Dispatcher
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.usecase.GetMovieDetailsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {

    @RelaxedMockK
    private lateinit var mockGetMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        movieDetailsViewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)
    }

    @Test
    fun `test getMovieDetails Success`() = runTest {
        val movieDetails = MovieDetails(
            id = 12344,
            backdropPath = "",
            posterPath = "",
            overview = "Overview of Movie 1",
            title = "Movie 1",
            releaseDate = "2022-10-09",
            tagline = "Movie 1 Tag",
            runtime = "120"
        )
        val apiResponse = ApiResponse.Success(movieDetails)
        coEvery { mockGetMovieDetailsUseCase(1) } returns flowOf(apiResponse)
        movieDetailsViewModel.getMovieDetails(1)
        assertEquals(
            apiResponse.data,
            (movieDetailsViewModel.movieDetailsStateFlow.value as ApiResponse.Success).data
        )
    }

    @Test
    fun `test getMovieDetails Error`() = runTest {
        val errorMsg="Internal Error"
        val apiResponse = ApiResponse.Error(errorMsg)
        coEvery { mockGetMovieDetailsUseCase(1) } returns flowOf(apiResponse)
        movieDetailsViewModel.getMovieDetails(1)
        assertEquals(
            apiResponse.message,
            (movieDetailsViewModel.movieDetailsStateFlow.value as ApiResponse.Error).message
        )
    }
}