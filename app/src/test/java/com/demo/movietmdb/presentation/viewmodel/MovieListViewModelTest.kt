package com.demo.movietmdb.presentation.viewmodel

import com.demo.movietmdb.Dispatcher
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.usecase.GetMoviesUseCase
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
    private lateinit var mockGetMoviesUseCase: GetMoviesUseCase

    private lateinit var movieListViewModel: MovieListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        movieListViewModel = MovieListViewModel(mockGetMoviesUseCase)
    }

    @Test
    fun `test getMovieList Success`() = runTest {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "posterpath1", "2023-07-26", "Movie 1"))
        movies.add(
            Movie(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        movies.add(
            Movie(3, "posterpath3", "2022-08-06", "Movie 3")
        )
        val apiResponse = ApiResponse.Success(MovieList(movies))
        val mappedResponse = ApiResponse.Success(movies)
        coEvery { mockGetMoviesUseCase() } returns flowOf(apiResponse)
        movieListViewModel.getMovieList()
        Assert.assertEquals(
            mappedResponse.data,
            (movieListViewModel.movieListStateFlow.value as ApiResponse.Success).data
        )
    }

    @Test
    fun `test getMovieList Error`() = runTest {
        val errorMsg="Internal Error"
        val apiResponse = ApiResponse.Error(errorMsg)
        coEvery { mockGetMoviesUseCase() } returns flowOf(apiResponse)
        movieListViewModel.getMovieList()
        Assert.assertEquals(
            apiResponse.message,
            (movieListViewModel.movieListStateFlow.value as ApiResponse.Error).message
        )
    }
}