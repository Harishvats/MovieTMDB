package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.data.BuildConfig
import com.demo.movietmdb.data.model.MovieDTO
import com.demo.movietmdb.data.model.MovieListDTO
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MovieRemoteDataSourceImplTest {
    @Mock
    private lateinit var mockTmdbService: com.demo.movietmdb.data.api.TMDBService

    @Mock
    private lateinit var mockMovieListDtoToModelMapper: com.demo.movietmdb.data.mapper.MovieListDtoToModelMapper

    @Mock
    private lateinit var mockMovieDetailsDtoToModelMapper: com.demo.movietmdb.data.mapper.MovieDetailsDtoToModelMapper

    private lateinit var movieRemoteDataSource: MovieRemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieRemoteDataSource =
            MovieRemoteDataSourceImpl(
                mockTmdbService,
                mockMovieListDtoToModelMapper,
                mockMovieDetailsDtoToModelMapper
            )
    }

    @Test
    fun `test get Movies list success`() = runTest {
        val moviesDTO = mutableListOf<MovieDTO>()
        moviesDTO.add(
           MovieDTO(
                1,
                "posterpath1",
                "2023-07-26",
                "Movie 1"
            )
        )
        moviesDTO.add(
            MovieDTO(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        moviesDTO.add(
            MovieDTO(3, "posterpath3", "2022-08-06", "Movie 3")
        )

        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "posterpath1", "2023-07-26", "Movie 1"))
        movies.add(
            Movie(2, "posterpath2", "2022-07-02", "Movie 2")
        )
        movies.add(
            Movie(3, "posterpath3", "2022-08-06", "Movie 3")
        )
        val movieListDto = MovieListDTO(movieDTO = moviesDTO)
        val expectedMovieList = MovieList(movies)

        `when`(mockTmdbService.getMovies(BuildConfig.API_KEY)).thenReturn(
            Response.success(
                movieListDto
            )
        )
        `when`(mockMovieListDtoToModelMapper.mapFrom(movieListDto)).thenReturn(expectedMovieList)

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        // Assert
        assertEquals(ApiResponse.Success(expectedMovieList), result)
    }

    @Test
    fun `test getMovies HTTP error`() = runTest {
        // Arrange
        val response = Response.error<MovieListDTO>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mockTmdbService.getMovies(BuildConfig.API_KEY)).thenReturn(response)

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        assertEquals(
            "Response.error()",
            (result as ApiResponse.Error).message
        )
    }

    @Test
    fun `test getMovies HttpException`() = runTest {
        val response = Response.error<MovieListDTO>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
        )
        given(mockTmdbService.getMovies(BuildConfig.API_KEY)).willAnswer {
            throw HttpException(
                response
            )
        }

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        assertEquals(
            ApiResponse.Error("HTTP 404 Response.error()"),
            result
        )
    }

    @Test
    fun `test getMovies IOException`() = runTest {

        given(mockTmdbService.getMovies(BuildConfig.API_KEY)).willAnswer { throw IOException("IO Error") }

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        assertEquals(ApiResponse.Error("IO Error"), result)
    }

    @Test
    fun `test getMovieDetails success`() = runTest {
        // Arrange
        val movieId = 123
        val movieDetailsDto =
            com.demo.movietmdb.data.model.MovieDetailsDTO(
                "",
                123,
                "Overview",
                "posterpath1",
                "2023-07-26",
                122,
                "tagline",
                "Movie 1"
            )
        val expectedMovieDetails = MovieDetails(
            123,
            "Overview",
            "posterpath1",
            "tagline",
            "2023-07-26",
            "122",
            "Movie 1",
            ""
        )
        `when`(mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)).thenReturn(
            Response.success(movieDetailsDto)
        )
        `when`(mockMovieDetailsDtoToModelMapper.mapFrom(movieDetailsDto)).thenReturn(
            expectedMovieDetails
        )

        // Act
        val result = movieRemoteDataSource.getMovieDetails(movieId).last()

        assertEquals(ApiResponse.Success(expectedMovieDetails), result)
    }


    @Test
    fun `test getMovieDetails HTTP error`() = runTest {
        val movieId = 123
        val response = Response.error<com.demo.movietmdb.data.model.MovieDetailsDTO>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)).thenReturn(response)

        // Act
        val result = movieRemoteDataSource.getMovieDetails(movieId).last()

        assertEquals(
            "Response.error()",
            (result as ApiResponse.Error).message
        )

    }

    @Test
    fun `test getMovieDetails HttpException`() = runTest {
        val movieId = 123
        val response = Response.error<MovieListDTO>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
        )
        given(mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)).willAnswer {
            throw HttpException(
                response
            )
        }

        // Act
        val result = movieRemoteDataSource.getMovieDetails(movieId).last()

        assertEquals(
            ApiResponse.Error("HTTP 404 Response.error()"),
            result
        )
    }

    @Test
    fun `test getMovieDetails IOException`() = runTest {
        val movieId = 123
        given(mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)).willAnswer {
            throw IOException("IO Error")
        }

        // Act
        val result = movieRemoteDataSource.getMovieDetails(movieId).last()

        assertEquals(ApiResponse.Error("IO Error"), result)
    }

}