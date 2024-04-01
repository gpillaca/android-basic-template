package com.gpillaca.upcomingmovies.framework.server

import com.gpillaca.upcomingmovies.framework.server.MovieResponse
import com.gpillaca.upcomingmovies.framework.server.RemoteConnection
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource

class MovieServerDataSource(
    private val apiKey: String
) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): List<MovieResponse> {
        return RemoteConnection.service.listPopularMovies(
            apiKey,
            region
        ).results
    }

}
