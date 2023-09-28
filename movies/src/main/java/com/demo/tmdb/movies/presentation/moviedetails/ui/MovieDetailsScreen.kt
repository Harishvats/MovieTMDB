package com.demo.tmdb.movies.presentation.moviedetails.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.tmdb.movies.R
import com.demo.tmdb.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel


@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel,
    selectedMovieId: Int,
    modifier: Modifier
) {
    LaunchedEffect(key1 = true) {
        movieDetailsViewModel.getMovieDetails(selectedMovieId)
    }
    val context = LocalContext.current
    val result = movieDetailsViewModel.movieDetailsStateFlow.collectAsState()

    when (result.value) {
        is ApiResponse.Error -> Toast.makeText(
            context,
            (result.value as ApiResponse.Error).message,
            Toast.LENGTH_SHORT
        ).show()

        ApiResponse.Loading -> Toast.makeText(
            context,
            stringResource(id = R.string.fetching_details),
            Toast.LENGTH_SHORT
        ).show()

        is ApiResponse.Success -> MovieCard(
            (result.value as ApiResponse.Success<MovieDetails>).data,
            modifier
        )
    }
}

