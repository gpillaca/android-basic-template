package com.gpillaca.upcomingmovies.model.datasource

import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String
) {

    suspend fun findPopularMovies(region: String): List<Movie> {
        return RemoteConnection.service.listPopularMovies(
            apiKey,
            region
        ).results
    }

}
