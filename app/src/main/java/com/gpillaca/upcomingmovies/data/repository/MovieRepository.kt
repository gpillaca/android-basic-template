package com.gpillaca.upcomingmovies.data.repository

import android.app.Application
import com.gpillaca.upcomingmovies.AppUpComingMovies
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.data.Movie
import com.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import com.gpillaca.upcomingmovies.data.tryCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.gpillaca.upcomingmovies.data.database.Movie as MovieDatabase

class MovieRepository(private val application: Application) {

    private val regionRepository by lazy {
        RegionRepository(application)
    }
    private val movieDao = (application as AppUpComingMovies).db.movieDao()
    private val movieLocalDataSource = MovieLocalDataSource(movieDao)
    private val remoteDataSource = MovieRemoteDataSource(BuildConfig.API_KEY)

    val popularMovies = movieLocalDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        tryCall {
            if (movieLocalDataSource.isEmpty()) {
                val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
                movieLocalDataSource.save(movies.toLocalModel())
            }
        }
    }

    fun findMovie(id: Int) = movieLocalDataSource.findMovie(id)

    suspend fun switchFavorite(movie: Movie) = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite).toMovieDatabase()

        movieLocalDataSource.updateMovie(updatedMovie)
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
    voteCount,
    favorite
)