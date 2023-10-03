package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.data.BuildConfig
import com.demo.movietmdb.data.TestData
import com.demo.movietmdb.data.TestData.IOResponseErrorMessage
import com.demo.movietmdb.data.TestData.errorCode
import com.demo.movietmdb.data.TestData.httpResponseErrorMessage
import com.demo.movietmdb.data.TestData.id
import com.demo.movietmdb.data.TestData.movieDetails
import com.demo.movietmdb.data.TestData.movieDetailsDTO
import com.demo.movietmdb.data.TestData.responseBody
import com.demo.movietmdb.data.TestData.responseErrorMessage
import com.demo.movietmdb.data.api.TMDBService
import com.demo.movietmdb.data.mapper.MovieDetailsDtoToModelMapper
import com.demo.movietmdb.data.mapper.MovieListDtoToModelMapper
import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.data.model.MovieListDTO
import com.demo.movietmdb.domain.model.MovieList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class MovieRemoteDataSourceImplTest {
    @MockK
    private lateinit var mockTmdbService: TMDBService

    @MockK
    private lateinit var mockMovieListDtoToModelMapper: MovieListDtoToModelMapper

    @MockK
    private lateinit var mockMovieDetailsDtoToModelMapper: MovieDetailsDtoToModelMapper

    private lateinit var movieRemoteDataSource: MovieRemoteDataSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        movieRemoteDataSource =
            MovieRemoteDataSourceImpl(
                mockTmdbService,
                mockMovieListDtoToModelMapper,
                mockMovieDetailsDtoToModelMapper
            )
    }

    @Test
    fun `getMovies() on success returns flow of Success ApiResponse`() = runTest {
        val moviesDTO = listOf(TestData.movieDTO)
        val movies = listOf(TestData.movie)

        val movieListDto = MovieListDTO(movieDTO = moviesDTO)
        val expectedMovieList = MovieList(movies)

        coEvery { (mockTmdbService.getMovies(BuildConfig.API_KEY)) } returns (
                retrofit2.Response.success(
                    movieListDto
                )
                )
        coEvery { (mockMovieListDtoToModelMapper.mapFrom(movieListDto)) } returns (expectedMovieList)

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        // Assert
        assertEquals(com.demo.movietmdb.common.Response.Success(expectedMovieList), result)
    }

    @Test
    fun `getMovies() on error returns flow of Error ApiResponse`() = runTest {
        // Arrange
        val response = retrofit2.Response.error<MovieListDTO>(
            errorCode,
            "".toResponseBody(responseBody)
        )
        coEvery { (mockTmdbService.getMovies(BuildConfig.API_KEY)) } returns (response)

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        assertEquals(
            responseErrorMessage,
            (result as com.demo.movietmdb.common.Response.Error).message
        )
    }

    @Test
    fun `getMovies() on HttpException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {
            val response = retrofit2.Response.error<MovieListDTO>(
                errorCode,
                "".toResponseBody(responseBody)
            )
            coEvery {
                (mockTmdbService.getMovies(BuildConfig.API_KEY))
            } answers {
                throw HttpException(
                    response
                )
            }

            // Act
            val result = movieRemoteDataSource.getMovies().last()

            assertEquals(
                com.demo.movietmdb.common.Response.Error(httpResponseErrorMessage),
                result
            )
        }

    @Test
    fun `getMovies() on IOException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {

            coEvery {
                (mockTmdbService.getMovies(BuildConfig.API_KEY))
            } answers { throw IOException(IOResponseErrorMessage) }

            // Act
            val result = movieRemoteDataSource.getMovies().last()

            assertEquals(com.demo.movietmdb.common.Response.Error(IOResponseErrorMessage), result)
        }

    @Test
    fun `getMovieDetails on success in api call returns flow of Success movie details ApiResponse`() =
        runTest {
            // Arrange
            val movieId = id
            val movieDetailsDto = movieDetailsDTO
            val expectedMovieDetails = movieDetails
            coEvery { (mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)) } returns (
                    retrofit2.Response.success(movieDetailsDto)
                    )
            coEvery { (mockMovieDetailsDtoToModelMapper.mapFrom(movieDetailsDto)) } returns (
                    expectedMovieDetails
                    )

            // Act
            val result = movieRemoteDataSource.getMovieDetails(movieId).last()

            assertEquals(com.demo.movietmdb.common.Response.Success(expectedMovieDetails), result)
        }


    @Test
    fun `getMovieDetails on Error in api call returns flow of Error ApiResponse`() = runTest {
        val movieId = id
        val response = retrofit2.Response.error<MovieDetailsDTO>(
            errorCode,
            "".toResponseBody(responseBody)
        )
        coEvery {
            (mockTmdbService.getMovieDetails(
                movieId,
                BuildConfig.API_KEY
            ))
        } returns (response)

        // Act
        val result = movieRemoteDataSource.getMovieDetails(movieId).last()

        assertEquals(
            responseErrorMessage,
            (result as com.demo.movietmdb.common.Response.Error).message
        )

    }

    @Test
    fun `getMovieDetails on HttpException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {
            val movieId = id
            val response = retrofit2.Response.error<MovieListDTO>(
                errorCode,
                "".toResponseBody(responseBody)
            )
            coEvery { (mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)) } answers {
                throw HttpException(
                    response
                )
            }

            // Act
            val result = movieRemoteDataSource.getMovieDetails(movieId).last()

            assertEquals(
                com.demo.movietmdb.common.Response.Error(httpResponseErrorMessage),
                result
            )
        }

    @Test
    fun `getMovieDetails on IOException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {
            val movieId = id
            coEvery { (mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)) } answers {
                throw IOException(IOResponseErrorMessage)
            }

            // Act
            val result = movieRemoteDataSource.getMovieDetails(movieId).last()

            assertEquals(com.demo.movietmdb.common.Response.Error(IOResponseErrorMessage), result)
        }

}