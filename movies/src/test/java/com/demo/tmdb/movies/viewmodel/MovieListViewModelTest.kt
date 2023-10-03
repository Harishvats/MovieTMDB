package com.demo.tmdb.movies.viewmodel

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.common.ViewState
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.usecase.GetMoviesListUseCase
import com.demo.tmdb.movies.Dispatcher
import com.demo.tmdb.movies.TestData.errorMsg
import com.demo.tmdb.movies.TestData.movie
import com.demo.tmdb.movies.presentation.movielist.viewmodel.MovieListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieListViewModelTest {

    private val mockGetMoviesListUseCase: GetMoviesListUseCase = mockk(relaxed = true)

    private lateinit var movieListViewModel: MovieListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        movieListViewModel = MovieListViewModel(mockGetMoviesListUseCase)
    }

    @Test
    fun `getMovieList on Success returns Success ViewState`() = runTest {
        val movies = listOf(movie)
        val response = Response.Success(MovieList(movies))
        val mappedResponse = Response.Success(movies)
        coEvery { mockGetMoviesListUseCase() } returns flowOf(response)

        movieListViewModel.getMovieList()

        Assert.assertEquals(
            mappedResponse.data[0].id,
            (movieListViewModel.movieListStateFlow.value as ViewState.SuccessState).data[0].id
        )
    }

    @Test
    fun `getMovieList on Error returns Error ViewState`() = runTest {
        val response = Response.Error(errorMsg)
        coEvery { mockGetMoviesListUseCase() } returns flowOf(response)

        movieListViewModel.getMovieList()

        Assert.assertEquals(
            response.message,
            (movieListViewModel.movieListStateFlow.value as ViewState.ErrorState).message
        )
    }
}