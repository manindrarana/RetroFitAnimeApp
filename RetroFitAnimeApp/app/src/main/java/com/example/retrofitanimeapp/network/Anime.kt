package com.example.retrofitanimeapp.network

data class Anime(
    val title: String,
    val image_url: String?,
    val description: String?,
    val genres: List<String>?,
)