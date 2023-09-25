package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class GetMoviesUseCaseTest {

    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getMoviesUseCase = GetMoviesUseCase(mockMovieRepository)
    }

    @Test
    fun `test execute`() = runTest {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "posterpath1", "2023-07-26", "Movie 1"))
        movies.add(
            Movie(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        movies.add(
            Movie(3, "posterpath3", "2022-08-06", "Movie 3")
        )

        // Arrange
        val expectedResponse = ApiResponse.Success(MovieList(movies))
        `when`(mockMovieRepository.getMovies()).thenReturn(flow { emit(expectedResponse) })

        // Act
        val result = getMoviesUseCase.execute()

        // Assert
        result.collect { response ->
            assert(response is ApiResponse.Success)
            val data = (response as ApiResponse.Success).data
            assert(data.movies.size == 3)
            assert(data.movies[0].title == "Movie 1")
        }
    }
}