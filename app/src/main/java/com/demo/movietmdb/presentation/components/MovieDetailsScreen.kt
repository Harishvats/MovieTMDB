package com.demo.movietmdb.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.demo.movietmdb.R
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.presentation.viewmodel.MovieDetailsViewModel


@Composable
fun MovieDetailsScreen(movieDetailsViewModel: MovieDetailsViewModel) {

    val result = movieDetailsViewModel.movieDetailsStateFlow.collectAsState()

    when (result.value) {
        is ApiResponse.Error -> Log.d("Harish", result.toString())
        ApiResponse.Loading -> Log.d("Harish", "Loading...")
        is ApiResponse.Success -> MovieDetailsWithTopBar((result.value as ApiResponse.Success<MovieDetails>).data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsWithTopBar(movieDetails: MovieDetails) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = movieDetails.title, maxLines = 1)
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.backicon))
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.Transparent,
            )
        )
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            MovieCard(movieDetails = movieDetails)
        }

    })
}

@Composable
fun MovieCard(movieDetails: MovieDetails) {

    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberAsyncImagePainter(movieDetails.backdropPath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            movieDetails.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            color = Color.Black, maxLines = 1
        )
        Text(
            movieDetails.tagline,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            color = Color.Gray, maxLines = 1
        )

        Text(
            stringResource(id = R.string.overview),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 10.dp, end = 10.dp),
            color = Color.Black,
        )


        Text(
            movieDetails.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = Color.Black,
        )

        Text(
            stringResource(id = R.string.about_movie),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 10.dp, end = 10.dp),
            color = Color.Black,
        )

        Text(
            stringResource(id = R.string.release_date),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            color = Color.Black,
        )

        Text(
            movieDetails.releaseDate,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 10.dp, end = 10.dp),
            color = Color.Black,
        )

        Text(
            stringResource(id = R.string.run_time),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            color = Color.Black,
        )

        Text(
            movieDetails.runtime.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 10.dp, end = 10.dp),
            color = Color.Black,
        )

    }
}