package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.domain.repository.MovieRepository

class GetMovieDetailsUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(movieId: String) = movieRepository.getMovieDetails(movieId)
}