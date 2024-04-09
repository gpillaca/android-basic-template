package android.gpillaca.upcomingmovies.viewmodel

import app.cash.turbine.test
import android.gpillaca.upcomingmovies.CoroutineTestRule
import android.gpillaca.upcomingmovies.movieStub
import android.gpillaca.upcomingmovies.ui.detail.MovieDetailViewModel
import android.gpillaca.upcomingmovies.ui.detail.MovieDetailViewModel.UiState
import android.gpillaca.upcomingmovies.usecase.FindMovieUseCase
import android.gpillaca.upcomingmovies.usecase.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Test class of [MovieDetailViewModel]
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var findMovieUseCase: FindMovieUseCase

    @Mock
    lateinit var switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase

    lateinit var movieDetailViewModel: MovieDetailViewModel

    private val movie = movieStub.copy(id = 2)


    @Test
    fun `UI is updated with the movie on start`() = runTest {
        movieDetailViewModel = buildViewModel()
        movieDetailViewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = movie), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        movieDetailViewModel = buildViewModel()
        movieDetailViewModel.onFavoriteClicked()
        runCurrent()

        verify(switchMovieFavoriteUseCase).invoke(movie)
    }


    private fun buildViewModel(): MovieDetailViewModel {
        whenever(findMovieUseCase(2)).thenReturn(flowOf(movie))
        return MovieDetailViewModel(2, findMovieUseCase, switchMovieFavoriteUseCase)
    }
}