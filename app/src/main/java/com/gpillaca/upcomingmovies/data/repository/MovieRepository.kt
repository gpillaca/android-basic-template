package com.gpillaca.upcomingmovies.data.repository

import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.domain.tryCall
import com.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import com.gpillaca.upcomingmovies.domain.Error
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

    suspend fun requestPopularMovies(): Error? {
        if (movieLocalDataSource.isEmpty()) {
            val movies = movieRemoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            movies.fold(ifLeft = { it }) {
                movieLocalDataSource.save(it)
            }
        }
        return null
    }

    fun findMovie(id: Int) = movieLocalDataSource.findMovie(id)

    suspend fun switchFavorite(movie: Movie) = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        movieLocalDataSource.updateMovie(updatedMovie)
    }
}
