package com.example.retrofitanimeapp.network

data class AnimeResponse(
    val data: List<Anime>
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages
)

data class AnimeImages(
    val jpg: AnimeImageDetails
)

data class AnimeImageDetails(
    val image_url: String
)