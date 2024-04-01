package com.gpillaca.upcomingmovies.data.repository

import com.gpillaca.upcomingmovies.framework.server.MovieResponse
import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.domain.tryCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.gpillaca.upcomingmovies.framework.database.MovieDB
import com.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {

    val popularMovies = movieLocalDataSource.movies.map { it.toMoviesDomain() }

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        tryCall {
            if (movieLocalDataSource.isEmpty()) {
                val movies: List<MovieResponse> = movieRemoteDataSource.findPopularMovies(regionRepository.findLastRegion())
                movieLocalDataSource.save(movies.toMoviesDatabase())
            }
        }
    }

    fun findMovie(id: Int) = movieLocalDataSource.findMovie(id).map {
        it.toMovieDomain()
    }

    suspend fun switchFavorite(movie: Movie) = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite).toMovieDatabase()

        movieLocalDataSource.updateMovie(updatedMovie)
    }
}

fun List<MovieResponse>.toMoviesDatabase(): List<MovieDB> {
    return map { movie ->
        movie.toMovieDatabase()
    }
}
fun List<MovieDB>.toMoviesDomain(): List<Movie> {
    return map { movie ->
        movie.toMovieDomain()
    }
}

private fun MovieResponse.toMovieDatabase() = MovieDB(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

private fun MovieDB.toMovieDomain() = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

private fun Movie.toMovieDatabase() = MovieDB(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)
