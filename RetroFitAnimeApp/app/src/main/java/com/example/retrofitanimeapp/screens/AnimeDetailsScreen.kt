package com.example.retrofitanimeapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.ui.theme.ButtonTextColor
import com.example.retrofitanimeapp.ui.theme.Typography

@Composable
fun AnimeDetailsScreen(animeId: Int, animeList: List<Anime>) {
    val animeDetails = animeList.find { it.title.text.hashCode() == animeId }

    if (animeDetails != null) {
        AnimeDetailsContent(animeDetails = animeDetails)
    } else {
        ErrorScreen(errorMessage = "Anime not found.")
    }
}

@Composable
fun AnimeDetailsContent(animeDetails: Anime) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = animeDetails.title.text ?: "Unknown Title",
            fontSize = 24.sp,
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Studio: ${animeDetails.studio ?: "N/A"}",
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Genres: ${animeDetails.genres?.joinToString() ?: "N/A"}",
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Hype: ${animeDetails.hype ?: "N/A"}",
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = animeDetails.description ?: "No description available.",
            style = Typography.bodyLarge
        )
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = errorMessage,
            modifier = Modifier.align(Alignment.Center),
            style = Typography.bodyLarge,
            color = ButtonTextColor
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Loading...",
            modifier = Modifier.align(Alignment.Center),
            style = Typography.bodyLarge,
            color = ButtonTextColor
        )
    }
}