package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.data.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.popularMovies
}
