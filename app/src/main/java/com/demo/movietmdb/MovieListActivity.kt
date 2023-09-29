package com.demo.movietmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.demo.tmdb.movies.presentation.navigation.AppNavHost
import com.demo.movietmdb.presentation.ui.theme.MovieTMDBTheme
import com.demo.tmdb.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import com.demo.tmdb.movies.presentation.movielist.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : ComponentActivity() {

    private val movieListViewModel: MovieListViewModel by viewModels()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MovieTMDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name), maxLines = 1)
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.Transparent,
                            )
                        )
                    }, content = {
                        AppNavHost(
                            movieListViewModel = movieListViewModel,
                            movieDetailsViewModel = movieDetailsViewModel,
                            modifier = Modifier.padding(it)
                        )
                    }
                    )
                }

            }
        }
    }
}


