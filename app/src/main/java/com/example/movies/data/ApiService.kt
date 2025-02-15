package com.example.movies.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// ApiService.kt
interface ApiService {
    @GET("list-titles")
    suspend fun getTitles(
        @Query("apiKey") apiKey: String,
        @Query("types") type: String // "movie" or "tv_series"
    ): TitlesResponse
    @GET("title/{id}/details/")
    suspend fun getTitleDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): TitleDetailsResponse


    companion object {
        const val BASE_URL = "https://api.watchmode.com/v1/"
        const val API_KEY = "Nq9QvAQlzbzMXUaFiaDveYvN9XOoUq50gLRxvjvJ" // Replace with your API key
    }
}

// TitlesResponse.kt
data class TitlesResponse(
    val titles: List<Title>
)
