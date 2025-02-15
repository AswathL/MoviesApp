package com.example.movies.data

import javax.inject.Inject


// MovieRepository.kt
class MovieRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovies(): List<Title> {
        return apiService.getTitles(ApiService.API_KEY, "movie").titles
    }

    suspend fun getTVShows(): List<Title> {
        return apiService.getTitles(ApiService.API_KEY, "tv_special").titles
    }

    suspend fun getTitleDetails(id: Int): TitleDetailsResponse {
        return apiService.getTitleDetails(id)
    }
}