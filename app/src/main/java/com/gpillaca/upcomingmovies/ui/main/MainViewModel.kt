package com.gpillaca.upcomingmovies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gpillaca.upcomingmovies.data.Error
import com.gpillaca.upcomingmovies.data.database.Movie as MovieDatabase
import com.gpillaca.upcomingmovies.data.Movie
import com.gpillaca.upcomingmovies.data.toError
import com.gpillaca.upcomingmovies.usecase.GetPopularMoviesUseCase
import com.gpillaca.upcomingmovies.usecase.RequestPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )

    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { movies ->
                    _state.update { UiState(movies = movies.toMovieList()) }
                }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(requestPopularMoviesUseCase, getPopularMoviesUseCase) as T
    }

}

private fun List<MovieDatabase>.toMovieList(): List<Movie> {
    return map { movie ->
        movie.toMovie()
    }
}

fun MovieDatabase.toMovie() = Movie(
    adult,
    backdropPath,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount,
    favorite
)