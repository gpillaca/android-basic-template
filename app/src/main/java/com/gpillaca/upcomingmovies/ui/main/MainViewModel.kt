package com.gpillaca.upcomingmovies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpillaca.upcomingmovies.domain.Error
import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.domain.toError
import com.gpillaca.upcomingmovies.usecase.GetPopularMoviesUseCase
import com.gpillaca.upcomingmovies.usecase.RequestPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Test class [MainViewModelTest]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
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
                    _state.update { UiState(movies = movies) }
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
