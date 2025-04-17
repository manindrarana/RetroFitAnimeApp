package com.example.retrofitanimeapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.retrofitanimeapp.model.MainViewModel
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.ui.theme.AppPrimaryColor
import com.example.retrofitanimeapp.ui.theme.ButtonColor
import com.example.retrofitanimeapp.ui.theme.ButtonTextColor
import com.example.retrofitanimeapp.ui.theme.Typography

@Composable
fun AnimeScreen(modifier: Modifier = Modifier, onAnimeClick: (Int) -> Unit) {
    val viewModel: MainViewModel = viewModel()
    val viewState by viewModel.animeState

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Anime", style = Typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                viewModel.fetchAnimeList()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor,
                contentColor = ButtonTextColor
            )
        ) {
            Text("Search", style = Typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                viewState.error != null -> {
                    Text(
                        text = "Error: ${viewState.error}\nPlease check the internet connection.",
                        modifier = Modifier.align(Alignment.Center),
                        style = Typography.bodyLarge,
                        color = ButtonTextColor
                    )
                }

                else -> {
                    val filteredList = viewState.list.filter { anime ->
                        anime.title.contains(searchQuery.text, ignoreCase = true)
                    }
                    if (filteredList.isEmpty()) {
                        Text(
                            text = "No anime found.",
                            modifier = Modifier.align(Alignment.Center),
                            style = Typography.bodyLarge,
                            color = ButtonTextColor
                        )
                    } else {
                        AnimeGrid(animeList = filteredList, onAnimeClick = onAnimeClick)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeGrid(animeList: List<Anime>, onAnimeClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(animeList) { anime ->
            AnimeItem(anime = anime, onClick = onAnimeClick)
        }
    }
}

@Composable
fun AnimeItem(anime: Anime, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(anime.mal_id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
            contentDescription = anime.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = anime.title,
            modifier = Modifier.padding(top = 8.dp),
            style = Typography.bodyLarge,
            color = ButtonTextColor
        )
    }
}

