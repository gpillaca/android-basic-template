package com.gpillaca.upcomingmovies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gpillaca.upcomingmovies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieDetailViewModel(
    private val movie: Movie
): ViewModel() {

    data class UiState(
        val movie: Movie
    )

    private val _state = MutableStateFlow(UiState(movie))
    val state: StateFlow<UiState> = _state.asStateFlow()
}

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(
    private val movie: Movie
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movie) as T
    }
}
