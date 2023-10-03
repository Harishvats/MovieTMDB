package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.common.Response
import com.demo.movietmdb.domain.model.MovieList
import com.demo.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMoviesListUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): Flow<Response<MovieList>> = movieRepository.getMovies()
}   



