package com.example.retrofitanimeapp.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitanimeapp.network.Anime
import com.example.retrofitanimeapp.network.AnimeApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _animeState = mutableStateOf(AnimeState())
    val animeState: State<AnimeState> = _animeState

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/manindrarana/anime-list-api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val animeApi = retrofit.create(AnimeApiService::class.java)

    fun fetchAnimeList() {
        viewModelScope.launch {
            try {
                val animeList = animeApi.getAnimeList()
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

    data class AnimeState(
        val loading: Boolean = true,
        val list: List<Anime> = emptyList(),
        val error: String? = null
    )
}

