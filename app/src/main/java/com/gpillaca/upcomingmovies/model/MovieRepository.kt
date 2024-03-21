package com.gpillaca.upcomingmovies.model

import androidx.appcompat.app.AppCompatActivity
import com.gpillaca.upcomingmovies.BuildConfig

class MovieRepository(private val activity: AppCompatActivity) {

    private val regionRepository by lazy {
        RegionRepository(activity)
    }

    suspend fun findPopularMovies(): List<Movie> {
        return RemoteConnection.service.listPopularMovies(
            BuildConfig.API_KEY,
            regionRepository.findLastRegion()
        ).results
    }
}
