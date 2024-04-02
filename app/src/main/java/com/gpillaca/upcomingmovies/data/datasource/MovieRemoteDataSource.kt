package com.gpillaca.upcomingmovies.data.datasource

import com.gpillaca.upcomingmovies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): List<Movie>
}
