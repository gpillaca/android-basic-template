package com.gpillaca.upcomingmovies.usecase

import com.gpillaca.upcomingmovies.data.repository.MovieRepository
import com.gpillaca.upcomingmovies.domain.Movie
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(id: Int): Flow<Movie> = movieRepository.findMovie(id)
}
