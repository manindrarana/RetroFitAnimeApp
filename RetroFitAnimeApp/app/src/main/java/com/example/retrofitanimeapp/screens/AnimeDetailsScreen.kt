package com.example.retrofitanimeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.ui.theme.AppPrimaryColor
import com.example.retrofitanimeapp.ui.theme.ButtonTextColor
import com.example.retrofitanimeapp.ui.theme.Typography
import androidx.navigation.NavHostController
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AnimeDetailsScreen(animeId: Int, animeList: List<Anime>, navController: NavHostController) {
    val animeDetails = animeList.find { it.title.hashCode() == animeId }

    if (animeDetails != null) {
        AnimeDetailsContent(animeDetails = animeDetails, navController = navController)
    } else {
        ErrorScreen(errorMessage = "Anime not found.")
    }
}

@Composable
fun AnimeDetailsContent(animeDetails: Anime, navController: NavHostController) {
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = ButtonTextColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
        }

        if (!animeDetails.image_url.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(animeDetails.image_url),
                contentDescription = animeDetails.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height((0.25f * screenHeight).dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((0.25f * screenHeight).dp)
                    .background(ButtonTextColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Image",
                    style = Typography.bodyLarge,
                    color = AppPrimaryColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = animeDetails.title,
                fontSize = 24.sp,
                style = Typography.bodyLarge,
                color = ButtonTextColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Genres: ${animeDetails.genres?.joinToString() ?: "N/A"}",
                style = Typography.bodyLarge,
                color = ButtonTextColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = animeDetails.description ?: "No description available.",
                style = Typography.bodyLarge,
                color = ButtonTextColor
            )
        }
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