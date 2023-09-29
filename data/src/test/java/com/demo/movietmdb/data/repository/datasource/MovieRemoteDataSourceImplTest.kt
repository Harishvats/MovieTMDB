package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.data.BuildConfig
import com.demo.movietmdb.data.api.TMDBService
import com.demo.movietmdb.data.mapper.MovieDetailsDtoToModelMapper
import com.demo.movietmdb.data.mapper.MovieListDtoToModelMapper
import com.demo.movietmdb.data.model.MovieDTO
import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.data.model.MovieListDTO
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
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

        coEvery { (mockTmdbService.getMovies(BuildConfig.API_KEY)) } returns (
                Response.success(
                    movieListDto
                )
                )
        coEvery { (mockMovieListDtoToModelMapper.mapFrom(movieListDto)) } returns (expectedMovieList)

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        // Assert
        assertEquals(ApiResponse.Success(expectedMovieList), result)
    }

    @Test
    fun `getMovies() on error returns flow of Error ApiResponse`() = runTest {
        // Arrange
        val response = Response.error<MovieListDTO>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
        )
        coEvery { (mockTmdbService.getMovies(BuildConfig.API_KEY)) } returns (response)

        // Act
        val result = movieRemoteDataSource.getMovies().last()

        assertEquals(
            "Response.error()",
            (result as ApiResponse.Error).message
        )
    }

    @Test
    fun `getMovies() on HttpException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {
            val response = Response.error<MovieListDTO>(
                404,
                "".toResponseBody("application/json".toMediaTypeOrNull())
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
                ApiResponse.Error("HTTP 404 Response.error()"),
                result
            )
        }

    @Test
    fun `getMovies() on IOException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {

            coEvery {
                (mockTmdbService.getMovies(BuildConfig.API_KEY))
            } answers { throw IOException("IO Error") }

            // Act
            val result = movieRemoteDataSource.getMovies().last()

            assertEquals(ApiResponse.Error("IO Error"), result)
        }

    @Test
    fun `getMovieDetails on success in api call returns flow of Success movie details ApiResponse`() =
        runTest {
            // Arrange
            val movieId = 123
            val movieDetailsDto =
                MovieDetailsDTO(
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
            coEvery { (mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)) } returns (
                    Response.success(movieDetailsDto)
                    )
            coEvery { (mockMovieDetailsDtoToModelMapper.mapFrom(movieDetailsDto)) } returns (
                    expectedMovieDetails
                    )

            // Act
            val result = movieRemoteDataSource.getMovieDetails(movieId).last()

            assertEquals(ApiResponse.Success(expectedMovieDetails), result)
        }


    @Test
    fun `getMovieDetails on Error in api call returns flow of Error ApiResponse`() = runTest {
        val movieId = 123
        val response = Response.error<MovieDetailsDTO>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
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
            "Response.error()",
            (result as ApiResponse.Error).message
        )

    }

    @Test
    fun `getMovieDetails on HttpException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {
            val movieId = 123
            val response = Response.error<MovieListDTO>(
                404,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )
            coEvery { (mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)) } answers {
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
    fun `getMovieDetails on IOException in api call returns flow of Error ApiResponse with exception message`() =
        runTest {
            val movieId = 123
            coEvery { (mockTmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)) } answers {
                throw IOException("IO Error")
            }

            // Act
            val result = movieRemoteDataSource.getMovieDetails(movieId).last()

            assertEquals(ApiResponse.Error("IO Error"), result)
        }

}