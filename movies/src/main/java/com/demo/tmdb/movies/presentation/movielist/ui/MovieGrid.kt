package com.demo.tmdb.movies.presentation.movielist.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demo.movietmdb.domain.model.Movie

@Composable
fun MoviesGrid(movieList: List<Movie>, selectedMovie: (Int) -> Unit, modifier: Modifier) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        content = {
            items(movieList.size) {
                MovieGridItem(movie = movieList[it], selectedMovie)
            }
        })
}