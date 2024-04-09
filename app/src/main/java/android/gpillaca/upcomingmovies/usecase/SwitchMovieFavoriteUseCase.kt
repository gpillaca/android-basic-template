package android.gpillaca.upcomingmovies.usecase

import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Test class [SwitchMovieFavoriteUseCaseTest]
 */
class SwitchMovieFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movie: Movie) {
        movieRepository.switchFavorite(movie)
    }
}
