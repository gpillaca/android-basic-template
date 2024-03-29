package com.gpillaca.upcomingmovies.model

import android.app.Application
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.model.database.Movie

class MovieRepository(private val application: Application) {

    private val regionRepository by lazy {
        RegionRepository(application)
    }

    suspend fun findPopularMovies(): List<Movie> {
        return RemoteConnection.service.listPopularMovies(
            BuildConfig.API_KEY,
            regionRepository.findLastRegion()
        ).results
    }
}
