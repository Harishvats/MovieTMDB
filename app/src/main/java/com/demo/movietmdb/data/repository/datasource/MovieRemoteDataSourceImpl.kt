package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.BuildConfig
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.data.api.TMDBService
import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.domain.mapper.MovieListDtoToModelMapper
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieListDtoToModelMapper: MovieListDtoToModelMapper
) : MovieRemoteDataSource {

    override suspend fun getMovies(): Flow<ApiResponse<MovieList>> {

        return flow {
            emit(ApiResponse.Loading)
            try {
                val response = tmdbService.getMovies(BuildConfig.API_KEY)
                if (response.isSuccessful) {
                    response.body()
                        ?.let {
                            emit(
                                ApiResponse.Success(
                                    movieListDtoToModelMapper.mapFrom(it)
                                )
                            )
                        }
                } else {
                    emit(ApiResponse.Error(response.message()))
                }

            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))

            } catch (e: IOException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))

            }
        }

    }


    override suspend fun getMovieDetails(movieId: String): Flow<ApiResponse<MovieDetailsDTO>> {

        return flow {
            emit(ApiResponse.Loading)
            try {

                val response = tmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)
                if (response.isSuccessful) {
                    emit(ApiResponse.Success(response.body()!!))
                } else {
                    emit(ApiResponse.Error(response.message()))
                }

            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))

            } catch (e: IOException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))

            }
        }
    }
}