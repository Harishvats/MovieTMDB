package com.demo.movietmdb.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    data: Any?,
    contentDescription: String? = null,
    contentScale: ContentScale
) {
    Image(
        painter = rememberAsyncImagePainter(data),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}