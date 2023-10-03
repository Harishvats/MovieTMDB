package com.demo.tmdb.movies.viewmodel

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.common.ViewState
import com.demo.movietmdb.domain.usecase.GetMovieDetailsUseCase
import com.demo.tmdb.movies.Dispatcher
import com.demo.tmdb.movies.TestData.errorMsg
import com.demo.tmdb.movies.TestData.movieDetails
import com.demo.tmdb.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {

    private val mockGetMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        movieDetailsViewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)
    }

    @Test
    fun `getMovieDetails on Success returns Success ViewState`() = runTest {
        val response = Response.Success(movieDetails)
        coEvery { mockGetMovieDetailsUseCase(1) } returns flowOf(response)

        movieDetailsViewModel.getMovieDetails(1)

        assertEquals(
            response.data,
            (movieDetailsViewModel.movieDetailsStateFlow.value as ViewState.SuccessState).data
        )
    }

    @Test
    fun `getMovieDetails on Error returns Error ViewState`() = runTest {
        val response = Response.Error(errorMsg)
        coEvery { mockGetMovieDetailsUseCase(1) } returns flowOf(response)

        movieDetailsViewModel.getMovieDetails(1)

        assertEquals(
            response.message,
            (movieDetailsViewModel.movieDetailsStateFlow.value as ViewState.ErrorState).message
        )
    }
}