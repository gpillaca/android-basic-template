package com.gpillaca.upcomingmovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val navigateTo: Movie? = null
    )

    private var _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() {
        if (_state.value?.movies == null) {
            refresh()
        }

        return _state
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(movies = movieRepository.findPopularMovies())
        }
    }

    fun onMovieClicked(movie: Movie) {
        _state.value = _state.value?.copy(navigateTo = movie)
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