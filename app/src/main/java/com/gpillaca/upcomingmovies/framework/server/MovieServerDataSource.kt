package com.gpillaca.upcomingmovies.framework.server

import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import com.gpillaca.upcomingmovies.di.ApiKey
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String
) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): List<MovieResponse> {
        return RemoteConnection.service.listPopularMovies(
            apiKey,
            region
        ).results
    }

}
