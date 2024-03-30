package com.gpillaca.upcomingmovies.data.datasource

import com.gpillaca.upcomingmovies.data.Movie
import com.gpillaca.upcomingmovies.data.RemoteConnection

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
