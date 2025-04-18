package com.example.retrofitanimeapp.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitanimeapp.network.Anime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _animeState = mutableStateOf(AnimeState())
    val animeState: State<AnimeState> = _animeState

    fun fetchAnimeList(context: Context) {
        viewModelScope.launch {
            try {
                val animeList = loadAnimeData(context)
                _animeState.value = _animeState.value.copy(
                    list = animeList,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading anime data: ${e.message}", e)
                _animeState.value = _animeState.value.copy(
                    loading = false,
                    error = "Error loading anime data: ${e.message}"
                )
            }
        }
    }

    private fun loadAnimeData(context: Context): List<Anime> {
        val json = context.assets.open("anime-data.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Anime>>() {}.type
        return Gson().fromJson(json, type)
    }

    data class AnimeState(
        val loading: Boolean = true,
        val list: List<Anime> = emptyList(),
        val error: String? = null
    )
}

