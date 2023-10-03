package com.demo.tmdb.movies.presentation.movielist.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.demo.movietmdb.common.ViewState
import com.demo.movietmdb.domain.model.Movie
import com.demo.tmdb.movies.R
import com.demo.tmdb.movies.presentation.movielist.viewmodel.MovieListViewModel

@Composable
fun MovieListScreen(
    movieListViewModel: MovieListViewModel,
    selectedMovie: (Int, String) -> Unit,
    modifier: Modifier
) {
    val resultValue = movieListViewModel.movieListStateFlow.collectAsState()
    val context = LocalContext.current

    when (resultValue.value) {
        is ViewState.ErrorState -> Toast.makeText(
            context,
            (resultValue.value as ViewState.ErrorState).message,
            Toast.LENGTH_SHORT
        ).show()

        is ViewState.LoadingState -> Toast.makeText(
            context,
            stringResource(id = R.string.fetching_movies),
            Toast.LENGTH_SHORT
        ).show()

        is ViewState.SuccessState -> MoviesGrid(
            (resultValue.value as ViewState.SuccessState<List<Movie>>).data,
            selectedMovie, modifier
        )
    }
}






