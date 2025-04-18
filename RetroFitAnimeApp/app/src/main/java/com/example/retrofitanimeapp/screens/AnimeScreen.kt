package com.example.retrofitanimeapp.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.retrofitanimeapp.model.MainViewModel
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.ui.theme.AppPrimaryColor
import com.example.retrofitanimeapp.ui.theme.ButtonTextColor
import com.example.retrofitanimeapp.ui.theme.Typography

@Composable
fun AnimeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onAnimeClick: (Int) -> Unit
) {
    val viewState by viewModel.animeState
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var sortOrder by remember { mutableStateOf("asc") }
    var expanded by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var isSidebarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchAnimeList(context)
    }

    Row(modifier = Modifier.fillMaxSize()) {
        if (isSidebarVisible) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp)
                    .background(AppPrimaryColor)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Genres",
                    style = Typography.bodyLarge,
                    color = ButtonTextColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                val genres = viewState.list.flatMap { it.genres ?: emptyList() }.distinct().sorted()
                genres.forEach { genre ->
                    Text(
                        text = genre,
                        style = Typography.bodyMedium,
                        color = if (selectedGenre == genre) AppPrimaryColor else ButtonTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedGenre = if (selectedGenre == genre) null else genre }
                            .padding(vertical = 4.dp)
                            .background(if (selectedGenre == genre) ButtonTextColor else AppPrimaryColor)
                            .padding(8.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(AppPrimaryColor)
                .padding(16.dp)
        ) {
            Button(
                onClick = { isSidebarVisible = !isSidebarVisible },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(if (isSidebarVisible) "Hide Sidebar" else "Show Sidebar")
            }

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Anime", style = Typography.bodyLarge) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { expanded = true }) {
                    Text("Sort By: ${if (sortOrder == "asc") "Ascending" else "Descending"}")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ascending") },
                        onClick = {
                            sortOrder = "asc"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Descending") },
                        onClick = {
                            sortOrder = "desc"
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    viewState.loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    viewState.error != null -> {
                        Text(
                            text = "Error: ${viewState.error}\nPlease check the data.",
                            modifier = Modifier.align(Alignment.Center),
                            style = Typography.bodyLarge,
                            color = ButtonTextColor
                        )
                    }

                    else -> {
                        val filteredList = viewState.list
                            .filter { anime ->
                                anime.title.text?.contains(searchQuery.text, ignoreCase = true) == true &&
                                        (selectedGenre == null || anime.genres?.contains(selectedGenre) == true)
                            }
                            .let { list ->
                                when (sortOrder) {
                                    "asc" -> list.sortedBy { it.title.text }
                                    "desc" -> list.sortedByDescending { it.title.text }
                                    else -> list
                                }
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
            .clickable { onClick(anime.title.text.hashCode()) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!anime.image_url.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(anime.image_url),
                contentDescription = anime.title.text,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
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
        Text(
            text = anime.title.text ?: "Unknown Title",
            modifier = Modifier.padding(top = 8.dp),
            style = Typography.bodyLarge,
            color = ButtonTextColor
        )
    }
}

