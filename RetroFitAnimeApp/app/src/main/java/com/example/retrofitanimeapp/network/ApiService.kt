package com.example.retrofitanimeapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.jikan.moe/v4/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val animeService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("anime")
    suspend fun getAnimeList(): AnimeResponse
}

