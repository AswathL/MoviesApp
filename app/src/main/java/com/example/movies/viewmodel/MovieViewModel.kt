package com.example.movies.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.MovieRepository
import com.example.movies.data.Title
import com.example.movies.data.TitleDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// MovieViewModel.kt
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = mutableStateOf<List<Title>>(emptyList())
    val movies: State<List<Title>> get() = _movies

    private val _tvShows = mutableStateOf<List<Title>>(emptyList())
    val tvShows: State<List<Title>> get() = _tvShows

    private val _posterCache = mutableStateOf<Map<Int, String>>(emptyMap())
    val posterCache: State<Map<Int, String>> get() = _posterCache

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val _titleDetails = mutableStateOf<TitleDetailsResponse?>(null)
    val titleDetails: State<TitleDetailsResponse?> get() = _titleDetails

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val moviesList = repository.getMovies()
                val tvShowsList = repository.getTVShows()

                _movies.value = moviesList
                _tvShows.value = tvShowsList
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchPosterForTitle(id: Int) {
        viewModelScope.launch {
            try {
                val details = repository.getTitleDetails(id)
                _posterCache.value = _posterCache.value + (id to (details.poster ?: ""))
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching poster for title $id: ${e.message}")
            }
        }
    }
    fun fetchTitleDetails(id: Int, onSuccess: ((TitleDetailsResponse) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val details = repository.getTitleDetails(id)
                _titleDetails.value = details
                onSuccess?.invoke(details)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
