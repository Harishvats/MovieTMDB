package com.demo.movietmdb.domain.mapper

import com.demo.movietmdb.common.AppConstants
import com.demo.movietmdb.common.Mapper
import com.demo.movietmdb.data.model.MovieDetailsDTO
import com.demo.movietmdb.domain.model.MovieDetails
import javax.inject.Inject

class MovieDetailsDtoToModelMapper @Inject constructor() : Mapper<MovieDetailsDTO, MovieDetails> {
    override fun mapFrom(from: MovieDetailsDTO): MovieDetails {
        return MovieDetails(
            id = from.id,
            overview = from.overview,
            releaseDate = from.releaseDate,
            runtime = from.runtime,
            tagline = from.tagline,
            posterPath = AppConstants.IMG_URL_PREFIX + from.posterPath,
            title = from.title,
            backdropPath = AppConstants.IMG_URL_PREFIX + from.backdropPath
        )
    }
}