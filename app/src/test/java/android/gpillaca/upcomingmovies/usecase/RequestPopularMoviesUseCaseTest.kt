package android.gpillaca.upcomingmovies.usecase

import android.gpillaca.upcomingmovies.data.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

/**
 * Test class of [RequestPopularMoviesUseCase]
 */
@RunWith(MockitoJUnitRunner::class)
class RequestPopularMoviesUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    @Before
    fun setUp() {
        requestPopularMoviesUseCase = RequestPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        requestPopularMoviesUseCase()

        verify(movieRepository).requestPopularMovies()
    }
}
