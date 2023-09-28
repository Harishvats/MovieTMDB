package com.demo.tmdb.movies.presentation.movielist.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.Movie
import com.demo.tmdb.movies.R
import com.demo.tmdb.movies.presentation.movielist.viewmodel.MovieListViewModel

@Composable
fun MovieListScreen(
    movieListViewModel: MovieListViewModel,
    selectedMovie: (Int) -> Unit,
    modifier: Modifier
) {
    val resultValue = movieListViewModel.movieListStateFlow.collectAsState()
    val context = LocalContext.current

    when (resultValue.value) {
        is ApiResponse.Error -> Toast.makeText(
            context,
            (resultValue.value as ApiResponse.Error).message,
            Toast.LENGTH_SHORT
        ).show()

        ApiResponse.Loading -> Toast.makeText(
            context,
            stringResource(id = R.string.fetching_movies),
            Toast.LENGTH_SHORT
        ).show()

        is ApiResponse.Success -> MoviesGrid(
            (resultValue.value as ApiResponse.Success<List<Movie>>).data,
            selectedMovie, modifier
        )
    }
}






