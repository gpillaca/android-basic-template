package android.gpillaca.upcomingmovies.usecase

import android.gpillaca.upcomingmovies.data.repository.MovieRepository
import android.gpillaca.upcomingmovies.movieStub
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * Test class of [GetPopularMoviesUseCase]
 */
@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `Invoke calls movies repository`() = runBlocking {
        val movieList = flowOf(listOf(movieStub.copy(id = 5)))
        whenever(movieRepository.popularMovies).thenReturn(movieList)

        val result = getPopularMoviesUseCase()

        Assert.assertEquals(movieList, result)
    }
}
