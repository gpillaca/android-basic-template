package android.gpillaca.upcomingmovies.usecase

import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.domain.common.Error
import android.gpillaca.upcomingmovies.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Test class [RequestPopularMoviesUseCaseTest]
 */
class RequestPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): Either<Error?, Unit> {
        return movieRepository.requestPopularMovies()
    }
}
