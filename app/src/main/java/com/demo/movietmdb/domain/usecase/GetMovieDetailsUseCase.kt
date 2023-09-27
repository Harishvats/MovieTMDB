package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int) = movieRepository.getMovieDetails(movieId)
}