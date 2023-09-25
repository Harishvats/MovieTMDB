package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun execute(): Flow<ApiResponse<MovieList>> = movieRepository.getMovies()
}   



