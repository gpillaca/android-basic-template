package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.domain.Error
import com.gpillaca.upcomingmovies.data.repository.MovieRepository
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): Error? {
        return movieRepository.requestPopularMovies()
    }
}
