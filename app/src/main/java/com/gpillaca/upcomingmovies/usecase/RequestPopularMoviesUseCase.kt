package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.domain.Error
import com.gpillaca.upcomingmovies.data.repository.MovieRepository

class RequestPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): Error? {
        return movieRepository.requestPopularMovies()
    }
}
