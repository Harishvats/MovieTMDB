package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun execute(movieId: String) = movieRepository.getMovieDetails(movieId)
}