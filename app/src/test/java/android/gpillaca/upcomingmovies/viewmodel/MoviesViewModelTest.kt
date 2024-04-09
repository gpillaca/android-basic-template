package android.gpillaca.upcomingmovies.viewmodel

import app.cash.turbine.test
import android.gpillaca.upcomingmovies.CoroutineTestRule
import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.movieStub
import android.gpillaca.upcomingmovies.ui.movies.MoviesViewModel
import android.gpillaca.upcomingmovies.ui.movies.MoviesViewModel.UiState
import android.gpillaca.upcomingmovies.usecase.GetPopularMoviesUseCase
import android.gpillaca.upcomingmovies.usecase.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * Test class of [MoviesViewModel]
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setUp() {
        moviesViewModel = MoviesViewModel(requestPopularMoviesUseCase, getPopularMoviesUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        val results = mutableListOf<UiState>()
        val movies = listOf(movieStub.copy(id = 5))

        moviesViewModel.onUiReady()
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))

        val job = launch {
            moviesViewModel.state.toList(results)
        }
        runCurrent()
        job.cancel()

        assertEquals(UiState(movies = movies), results.first())
    }

    @Test
    fun `Turbine_ State is updated with current cached content immediately`() = runTest {
        val movies = listOf(movieStub.copy(id = 5))
        moviesViewModel.onUiReady()
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))

        moviesViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = movies), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Requesting movies when cache content is empty`() = runTest {
        moviesViewModel.onUiReady()
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(emptyList()))
        whenever(requestPopularMoviesUseCase()).thenReturn(Either.Left(null))

        moviesViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when screen start and hidden when it finishes requesting movies`() = runTest {
        moviesViewModel.requestPopularMovies()
        whenever(requestPopularMoviesUseCase()).thenReturn(Either.Left(null))
        moviesViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            cancel()
        }
    }
}
