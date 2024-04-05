package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.Either
import com.gpillaca.upcomingmovies.domain.Error
import com.gpillaca.upcomingmovies.data.repository.MovieRepository
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
