package com.example.retrofitanimeapp.network

data class AnimeResponse(
    val data: List<Anime>
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val type: String?,
    val status: String?,
    val genres: List<Genre>?,
    val streaming: List<StreamingPlatform>?
)

data class AnimeImages(
    val jpg: AnimeImageDetails
)

data class AnimeImageDetails(
    val image_url: String
)

data class Genre(
    val name: String
)

data class StreamingPlatform(
    val name: String,
    val url: String
)