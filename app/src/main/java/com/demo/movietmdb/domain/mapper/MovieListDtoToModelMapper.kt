package com.demo.movietmdb.domain.mapper

import com.demo.movietmdb.common.AppConstants
import com.demo.movietmdb.common.Mapper
import com.demo.movietmdb.data.model.MovieDTO
import com.demo.movietmdb.data.model.MovieListDTO
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.model.MovieList
import javax.inject.Inject

class MovieListDtoToModelMapper @Inject constructor(private val movieDtoToModelMapper: MovieDtoToModelMapper) :
    Mapper<MovieListDTO, MovieList> {
    override fun mapFrom(from: MovieListDTO): MovieList {

        return MovieList(movies = from.movieDTO.map { data -> movieDtoToModelMapper.mapFrom(data) })
    }
}


class MovieDtoToModelMapper @Inject constructor() : Mapper<MovieDTO, Movie> {
    override fun mapFrom(from: MovieDTO): Movie {
        return Movie(
            id = from.id,
            posterPath = AppConstants.IMG_URL_PREFIX + from.posterPath,
            releaseDate = from.releaseDate,
            title = from.title,
            voteAverage = from.voteAverage
        )
    }
}