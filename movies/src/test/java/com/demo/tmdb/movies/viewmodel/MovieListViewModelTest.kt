package com.demo.tmdb.movies.viewmodel

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.common.ViewState
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.usecase.GetMoviesListUseCase
import com.demo.tmdb.movies.Dispatcher
import com.demo.tmdb.movies.TestData.errorMsg
import com.demo.tmdb.movies.TestData.movie
import com.demo.tmdb.movies.presentation.movielist.viewmodel.MovieListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieListViewModelTest {

    @RelaxedMockK
    private lateinit var mockGetMoviesListUseCase: GetMoviesListUseCase

    private lateinit var movieListViewModel: MovieListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        movieListViewModel = MovieListViewModel(mockGetMoviesListUseCase)
    }

    @Test
    fun `getMovieList on Success returns Success ViewState`() = runTest {
        val movies = listOf(movie)
        val apiResponse = ApiResponse.Success(MovieList(movies))
        val mappedResponse = ApiResponse.Success(movies)
        coEvery { mockGetMoviesListUseCase() } returns flowOf(apiResponse)
        movieListViewModel.getMovieList()
        Assert.assertEquals(
            mappedResponse.data[0].id,
            (movieListViewModel.movieListStateFlow.value as ViewState.SuccessState).data[0].id
        )
    }

    @Test
    fun `getMovieList on Error returns Error ViewState`() = runTest {
        val apiResponse = ApiResponse.Error(errorMsg)
        coEvery { mockGetMoviesListUseCase() } returns flowOf(apiResponse)
        movieListViewModel.getMovieList()
        Assert.assertEquals(
            apiResponse.message,
            (movieListViewModel.movieListStateFlow.value as ViewState.ErrorState).message
        )
    }
}