package com.example.movies.userInterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movies.viewmodel.MovieViewModel
import com.example.movies.data.TitleDetailsResponse

@Composable
fun DetailsScreen(itemId: Int, viewModel: MovieViewModel) {
    val titleDetails by viewModel.titleDetails
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    LaunchedEffect(itemId) {
        viewModel.fetchTitleDetails(itemId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(android.graphics.Color.parseColor("#2f2f39")))
            .padding(16.dp)
    ) {
        when {
            isLoading -> ShimmerEffect()
            error != null -> ErrorScreen(error!!)
            titleDetails != null -> TitleDetails(titleDetails!!)
        }
    }
}

@Composable
fun TitleDetails(title: TitleDetailsResponse) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Poster Image
            if (title.poster != null) {
                AsyncImage(
                    model = title.poster,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Title
            Text(
                text = title.title,
                style = TextStyle(color = Color.White, fontSize = 28.sp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Overview
            Text(
                text = "Overview",
                style = TextStyle(color = Color.White, fontSize = 22.sp)
            )
            Text(
                text = title.plot_overview ?: "No plot overview available",
                style = TextStyle(color = Color.LightGray, fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Movie Details Grid
            MovieDetailsGrid(title)
        }
    }
}

@Composable
fun MovieDetailsGrid(title: TitleDetailsResponse) {
    val details = listOf(
        "IMDB ID" to title.imdb_id,
        "Year" to title.year.toString(),
        "Runtime" to "${title.runtime_minutes} min",
        "Genres" to title.genres.joinToString(", "),
        "User Rating" to title.user_rating.toString(),
        "Critic Score" to title.critic_score.toString(),
        "US Rating" to title.us_rating,
        "Language" to title.original_language
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        details.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { (label, value) ->
                    DetailCard(label, value, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun DetailCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3E3E4A)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = label,
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = value,
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

