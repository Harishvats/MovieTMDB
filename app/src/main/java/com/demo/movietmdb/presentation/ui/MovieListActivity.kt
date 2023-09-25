package com.demo.movietmdb.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.demo.movietmdb.common.AppConstants
import com.demo.movietmdb.presentation.components.MovieListScreen
import com.demo.movietmdb.presentation.ui.theme.MovieTMDBTheme
import com.demo.movietmdb.presentation.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : ComponentActivity() {

    private val movieListViewModel: MovieListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MovieTMDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MovieListScreen(movieListViewModel) {
                        Log.d("Harish", "selected movie Id $it")
                        startActivity(
                            Intent(this, MovieDetailsActivity::class.java).putExtra(
                                AppConstants.SELECTED_MOVIE_ID,
                                it
                            )
                        )
                    }

                }
            }
        }
    }
}

