package com.demo.movietmdb.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.presentation.viewmodel.MovieListViewModel

@Composable
fun MovieListScreen(movieListViewModel: MovieListViewModel, selectedMovie: (Int) -> Unit) {
    val resultValue = movieListViewModel.movieListStateFlow.collectAsState()

    when (resultValue.value) {
        is ApiResponse.Error -> Log.d("MovieListScreen", resultValue.toString())
        ApiResponse.Loading -> Log.d("MovieListScreen", "Loading")
        is ApiResponse.Success -> MoviesGrid(
            (resultValue.value as ApiResponse.Success<List<Movie>>).data,
            selectedMovie
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesGrid(movieList: List<Movie>, selectedMovie: (Int) -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.Transparent,
            )
        )
    }, content = {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            content = {
                items(movieList.size) {
                    MovieGridItem(movie = movieList[it], selectedMovie)
                }
            })
    })
}

@Composable
fun MovieGridItem(movie: Movie, selectedMovie: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { selectedMovie(movie.id) }
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.posterPath),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                movie.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                color = Color.Black, maxLines = 1
            )
            Text(
                movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                color = Color.Gray
            )
        }
    }
}


