package com.demo.movietmdb.data.repository.datasource

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.data.BuildConfig
import com.demo.movietmdb.data.api.TMDBService
import com.demo.movietmdb.data.mapper.MovieDetailsDtoToModelMapper
import com.demo.movietmdb.data.mapper.MovieListDtoToModelMapper
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.model.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieListDtoToModelMapper: MovieListDtoToModelMapper,
    private val movieDetailsDtoToModelMapper: MovieDetailsDtoToModelMapper
) : MovieRemoteDataSource {

    override suspend fun getMovies(): Flow<Response<MovieList>> = flow {
        emit(Response.Loading)
        try {
            val response = tmdbService.getMovies(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()
                    ?.let {
                        emit(
                            Response.Success(
                                movieListDtoToModelMapper.mapFrom(it)
                            )
                        )
                    }
            } else {
                emit(Response.Error(response.message()))
            }

        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: ""))

        } catch (e: IOException) {
            emit(Response.Error(e.localizedMessage ?: ""))

        }
    }.flowOn(Dispatchers.IO)


    override suspend fun getMovieDetails(movieId: Int): Flow<Response<MovieDetails>> = flow {
        emit(Response.Loading)
        try {

            val response = tmdbService.getMovieDetails(movieId, BuildConfig.API_KEY)
            if (response.isSuccessful) {

                response.body()
                    ?.let { emit(Response.Success(movieDetailsDtoToModelMapper.mapFrom(it))) }
            } else {
                emit(Response.Error(response.message()))
            }

        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: ""))

        } catch (e: IOException) {
            emit(Response.Error(e.localizedMessage ?: ""))

        }
    }.flowOn(Dispatchers.IO)
}

