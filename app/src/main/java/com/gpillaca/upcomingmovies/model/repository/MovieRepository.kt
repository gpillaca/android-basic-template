package com.gpillaca.upcomingmovies.model.repository

import android.app.Application
import com.gpillaca.upcomingmovies.AppUpComingMovies
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.gpillaca.upcomingmovies.model.database.Movie as MovieDatabase

class MovieRepository(private val application: Application) {

    private val regionRepository by lazy {
        RegionRepository(application)
    }
    private val movieDao = (application as AppUpComingMovies).db.movieDao()
    private val movieLocalDataSource = MovieLocalDataSource(movieDao)
    private val remoteDataSource = MovieRemoteDataSource(BuildConfig.API_KEY, regionRepository)

    val popularMovies = movieLocalDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (movieLocalDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
            movieLocalDataSource.save(movies.toLocalModel())
        }
    }
}

private fun List<Movie>.toLocalModel(): List<MovieDatabase> {
    return map { movie ->
        movie.toMovieDatabase()
    }
}

private fun Movie.toMovieDatabase() = MovieDatabase(
    id,
    adult,
    backdropPath,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)