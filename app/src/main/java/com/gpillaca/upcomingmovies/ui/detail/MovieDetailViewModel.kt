package com.gpillaca.upcomingmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gpillaca.upcomingmovies.model.Movie

class MovieDetailViewModel(
    private val movie: Movie
): ViewModel() {

    data class UiState(
        val movie: Movie
    )

    private val _state = MutableLiveData(UiState(movie))
    val state: LiveData<UiState> get() = _state
}

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(
    private val movie: Movie
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movie) as T
    }
}
