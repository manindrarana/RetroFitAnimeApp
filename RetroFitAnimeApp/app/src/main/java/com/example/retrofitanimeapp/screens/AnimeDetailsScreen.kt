package com.example.retrofitanimeapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.network.animeService
import com.example.retrofitanimeapp.ui.theme.ButtonTextColor
import com.example.retrofitanimeapp.ui.theme.Typography

@Composable
fun AnimeDetailsScreen(animeId: Int) {
    val anime = remember { mutableStateOf<Anime?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(animeId) {
        try {
            Log.d("AnimeDetailsScreen", "Fetching details for animeId: $animeId")
            anime.value = animeService.getAnimeById(animeId)
            Log.d("AnimeDetailsScreen", "Fetched anime details: $anime")
        } catch (e: Exception) {
            Log.e("AnimeDetailsScreen", "Error fetching anime details: ${e.message}", e)
            errorMessage.value = "Failed to load anime details. Please try again."
        }
    }

    if (errorMessage.value != null) {
        ErrorScreen(errorMessage = errorMessage.value ?: "Unknown error")
    } else if (anime.value != null) {
        val animeDetails = anime.value!!
        AnimeDetailsContent(animeDetails = animeDetails)
    } else {
        LoadingScreen()
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
        Image(
            painter = rememberAsyncImagePainter(animeDetails.images.jpg.image_url),
            contentDescription = animeDetails.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = animeDetails.title,
            fontSize = 24.sp,
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Score: ${animeDetails.score ?: "N/A"}",
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Episodes: ${animeDetails.episodes ?: "N/A"}",
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Synopsis: ${animeDetails.synopsis ?: "N/A"}",
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