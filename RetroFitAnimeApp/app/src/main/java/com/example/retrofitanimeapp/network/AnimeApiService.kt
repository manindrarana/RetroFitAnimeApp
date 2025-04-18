package com.example.retrofitanimeapp.network

import retrofit2.http.GET

interface AnimeApiService {
    @GET("anime")
    suspend fun getAnimeList(): List<Anime>
}