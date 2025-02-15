package com.example.movies.userInterface

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movies.viewmodel.MovieViewModel
import com.example.movies.R
import com.example.movies.data.Title

@Composable
fun HomeScreen(viewModel: MovieViewModel, onItemClick: (Int) -> Unit) {
    val movies by viewModel.movies
    val tvShows by viewModel.tvShows
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val posterCache by viewModel.posterCache
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Content on top of the background
        Column() {
            if (isLoading) {
                ShimmerEffect()
            } else if (error != null) {
                ErrorScreen(error!!)
            } else {
                Spacer(modifier = Modifier.padding(top=10.dp))
                Text(text = "Welcome to Movie App",modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally), fontSize = 26.sp, color = Color.White)
                Text(text = "Movies", modifier = Modifier.padding(8.dp), fontSize = 20.sp, color = Color.White)
                LazyRow {
                    items(movies) { movie ->
                        val posterUrl = posterCache[movie.id]
                        TitleItemWithPoster(
                            title = movie,
                            posterUrl = posterUrl,
                            onItemClick = onItemClick,
                            fetchPoster = { viewModel.fetchPosterForTitle(movie.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "TV Shows", modifier = Modifier.padding(8.dp), fontSize = 20.sp, color = Color.White)
                LazyRow {
                    items(tvShows) { tvShow ->
                        val posterUrl = posterCache[tvShow.id]
                        TitleItemWithPoster(
                            title = tvShow,
                            posterUrl = posterUrl,
                            onItemClick = onItemClick,
                            fetchPoster = { viewModel.fetchPosterForTitle(tvShow.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TitleItemWithPoster(
    title: Title,
    posterUrl: String?,
    onItemClick: (Int) -> Unit,
    fetchPoster: () -> Unit
) {
    LaunchedEffect(title.id) {
        if (posterUrl == null) {
            fetchPoster()
        }
    }

    Card(
        modifier = Modifier
            .width(250.dp)
            .padding(8.dp)
            .clickable { onItemClick(title.id) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3E3E4A))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            if (posterUrl != null) {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title.title,color = Color.White)
            Text(text = "Year: ${title.year}",color = Color.White)
            Text(text = "IMDB ID: ${title.imdb_id}",color = Color.White)
        }
    }
}


@Composable
fun ErrorScreen(error: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error: $error", color = Color.Red)
    }
}

@Composable
fun ShimmerEffect() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value + 500, translateAnim.value + 500)
    )

    Column() {
        repeat(5) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = brush)
            )
        }
    }
}