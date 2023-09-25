package com.demo.movietmdb.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.demo.movietmdb.common.AppConstants
import com.demo.movietmdb.presentation.components.MovieDetailsScreen
import com.demo.movietmdb.presentation.ui.theme.MovieTMDBTheme
import com.demo.movietmdb.presentation.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedMovie = intent.getIntExtra(AppConstants.SELECTED_MOVIE_ID, 0)
        setContent {
            MovieTMDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    movieDetailsViewModel.getMovieDetails(selectedMovie)
                    MovieDetailsScreen(movieDetailsViewModel, selectedMovie)
                }
            }
        }
    }
}

