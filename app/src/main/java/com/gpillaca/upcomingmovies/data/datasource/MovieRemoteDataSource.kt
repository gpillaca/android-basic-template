package com.gpillaca.upcomingmovies.data.datasource

import com.gpillaca.upcomingmovies.framework.server.MovieResponse

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): List<MovieResponse>
}
