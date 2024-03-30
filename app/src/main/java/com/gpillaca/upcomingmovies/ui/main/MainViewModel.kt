package com.gpillaca.upcomingmovies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gpillaca.upcomingmovies.model.Error
import com.gpillaca.upcomingmovies.model.database.Movie as MovieDatabase
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.repository.MovieRepository
import com.gpillaca.upcomingmovies.model.toError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository
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
            movieRepository.popularMovies
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
            val error = movieRepository.requestPopularMovies()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
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