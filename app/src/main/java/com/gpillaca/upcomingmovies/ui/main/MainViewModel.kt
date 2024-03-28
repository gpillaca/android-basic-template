package com.gpillaca.upcomingmovies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val navigateTo: Movie? = null
    )

    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(movies = movieRepository.findPopularMovies())
        }
    }

    fun onMovieClicked(movie: Movie) {
        _state.value = _state.value.copy(navigateTo = movie)
    }

    fun onNavigationDone() {
        _state.value = _state.value.copy(navigateTo = null)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(movieRepository) as T
    }

}