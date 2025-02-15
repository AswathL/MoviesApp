package com.example.movies.data

data class TitleDetailsResponse(
    val id: Int,
    val title: String,
    val plot_overview: String?,
    val poster: String?,
    val imdb_id: String,
    val year: Int,
    val user_rating: Double,
    val runtime_minutes: Int,
    val genres: List<String>,
    val critic_score: Int,
    val us_rating:String,
    val original_language:String

)
