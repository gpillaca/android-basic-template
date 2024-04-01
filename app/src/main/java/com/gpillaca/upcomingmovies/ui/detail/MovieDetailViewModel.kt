package com.gpillaca.upcomingmovies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gpillaca.upcomingmovies.data.Movie
import com.gpillaca.upcomingmovies.ui.main.toMovie
import com.gpillaca.upcomingmovies.usecase.FindMovieUseCase
import com.gpillaca.upcomingmovies.usecase.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
): ViewModel() {

    data class UiState(val movie: Movie? = null)

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId).collect { movieDatabase ->
                _state.value = UiState(movieDatabase.toMovie())
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let {
                switchMovieFavoriteUseCase(it)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(
    private val movieId: Int,
    private val findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movieId, findMovieUseCase, switchMovieFavoriteUseCase) as T
    }
}
