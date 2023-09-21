package com.demo.movietmdb.domain.usecase

import com.demo.movietmdb.domain.repository.MovieRepository


class GetMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute() = movieRepository.getMovies()
}   



