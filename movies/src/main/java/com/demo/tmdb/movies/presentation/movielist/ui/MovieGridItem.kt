package com.demo.tmdb.movies.presentation.movielist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.presentation.components.CustomImage
import com.demo.movietmdb.presentation.components.CustomText

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
            CustomImage(
                data = movie.posterPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomText(
                movie.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                color = Color.Black, maxLines = 1
            )
            CustomText(
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