package android.gpillaca.upcomingmovies.usecase

import android.gpillaca.upcomingmovies.data.repository.MovieRepository
import android.gpillaca.upcomingmovies.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Test class [GetPopularMoviesUseCaseTest]
 */
class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = movieRepository.popularMovies
}
