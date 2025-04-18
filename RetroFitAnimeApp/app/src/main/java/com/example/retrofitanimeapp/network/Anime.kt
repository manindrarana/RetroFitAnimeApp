package com.example.retrofitanimeapp.network

data class Anime(
    val title: Title,
    val image_url: String?,
    val description: String?,
    val studio: String?,
    val genres: List<String>?,
    val hype: Int?,
    val start_date: String?
)

data class Title(
    val link: String?,
    val text: String?
)