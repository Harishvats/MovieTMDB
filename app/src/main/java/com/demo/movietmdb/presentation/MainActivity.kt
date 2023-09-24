package com.demo.movietmdb.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.usecase.GetMoviesUseCase
import com.demo.movietmdb.presentation.components.MoviesGrid
import com.demo.movietmdb.presentation.theme.MovieTMDBTheme
import com.demo.movietmdb.presentation.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var getMoviesUseCase: GetMoviesUseCase

    private val movieListViewModel: MovieListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MovieTMDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val resultValue = movieListViewModel.movieListStateFlow.collectAsState()

                    when (resultValue.value) {
                        is ApiResponse.Error -> Log.d("Harish", resultValue.toString())
                        ApiResponse.Loading -> Log.d("Harish", "Loading")
                        is ApiResponse.Success -> MoviesGrid((resultValue.value as ApiResponse.Success<List<Movie>>).data)
                    }

                }
            }
        }
    }
}

