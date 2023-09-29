package com.demo.tmdb.movies.viewmodel

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.common.ViewState
import com.demo.movietmdb.domain.usecase.GetMovieDetailsUseCase
import com.demo.tmdb.movies.TestData.errorMsg
import com.demo.tmdb.movies.TestData.movieDetails
import com.demo.tmdb.movies.Dispatcher
import com.demo.tmdb.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
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
    fun `getMovieDetails on Success returns Success ViewState`() = runTest {
        val apiResponse = ApiResponse.Success(movieDetails)
        coEvery { mockGetMovieDetailsUseCase(1) } returns flowOf(apiResponse)
        movieDetailsViewModel.getMovieDetails(1)
        assertEquals(
            apiResponse.data,
            (movieDetailsViewModel.movieDetailsStateFlow.value as ViewState.SuccessState).data
        )
    }

    @Test
    fun `getMovieDetails on Error returns Error ViewState`() = runTest {
        val apiResponse = ApiResponse.Error(errorMsg)
        coEvery { mockGetMovieDetailsUseCase(1) } returns flowOf(apiResponse)
        movieDetailsViewModel.getMovieDetails(1)
        assertEquals(
            apiResponse.message,
            (movieDetailsViewModel.movieDetailsStateFlow.value as ViewState.ErrorState).message
        )
    }
}