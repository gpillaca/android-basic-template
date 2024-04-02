package com.gpillaca.upcomingmovies.data.repository

import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.domain.tryCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import javax.inject.Inject

/**
 * Test class [MovieRepositoryTest]
 */
class MovieRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {

    val popularMovies = movieLocalDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        tryCall {
            if (movieLocalDataSource.isEmpty()) {
                val movies: List<Movie> = movieRemoteDataSource.findPopularMovies(regionRepository.findLastRegion())
                movieLocalDataSource.save(movies)
            }
        }
    }

    fun findMovie(id: Int) = movieLocalDataSource.findMovie(id)

    suspend fun switchFavorite(movie: Movie) = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        movieLocalDataSource.updateMovie(updatedMovie)
    }
}
