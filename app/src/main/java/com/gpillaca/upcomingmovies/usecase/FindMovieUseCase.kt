package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.data.repository.MovieRepository

class FindMovieUseCase(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(id: Int) = movieRepository.findMovie(id)
}
