package com.example.retrofitanimeapp.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.network.animeService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _animeState = mutableStateOf(AnimeState())
    val animeState: State<AnimeState> = _animeState

    init {
        fetchAnimeList()
    }

    fun fetchAnimeList() {
        viewModelScope.launch {
            try {
                val response = animeService.getAnimeList()
                Log.d("MainViewModel", "API Response: ${response.data}")
                // Limit the list to 10-12 animes
                val limitedList = response.data.take(12)
                _animeState.value = _animeState.value.copy(
                    list = limitedList,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching anime list: ${e.message}", e)
                _animeState.value = _animeState.value.copy(
                    loading = false,
                    error = "Error fetching anime list: ${e.message}"
                )
            }
        }
    }

    data class AnimeState(
        val loading: Boolean = true,
        val list: List<Anime> = emptyList(),
        val error: String? = null
    )
}

