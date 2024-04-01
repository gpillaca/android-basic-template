package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.data.Movie
import com.gpillaca.upcomingmovies.data.repository.MovieRepository

class SwitchMovieFavoriteUseCase(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movie: Movie) {
        movieRepository.switchFavorite(movie)
    }
}
