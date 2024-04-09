package android.gpillaca.upcomingmovies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.gpillaca.upcomingmovies.di.MovieId
import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.usecase.FindMovieUseCase
import android.gpillaca.upcomingmovies.usecase.SwitchMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Test class of [MovieDetailViewModelTest]
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    @MovieId private val movieId: Int,
    private val findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
): ViewModel() {

    data class UiState(val movie: Movie? = null)

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId).collect { movie ->
                _state.value = UiState(movie)
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
