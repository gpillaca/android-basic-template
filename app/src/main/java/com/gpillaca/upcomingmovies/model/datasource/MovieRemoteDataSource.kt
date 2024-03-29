package com.gpillaca.upcomingmovies.model.datasource

import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.RemoteConnection
import com.gpillaca.upcomingmovies.model.repository.RegionRepository

class MovieRemoteDataSource(
    private val apiKey: String,
    private val regionRepository: RegionRepository
) {

    suspend fun findPopularMovies(): List<Movie> {
        return RemoteConnection.service.listPopularMovies(
            apiKey,
            regionRepository.findLastRegion()
        ).results
    }

}
