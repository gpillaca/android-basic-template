package com.gpillaca.upcomingmovies.framework.database

import com.gpillaca.upcomingmovies.MovieMapper
import com.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.domain.Error
import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.domain.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRoomDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper
) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDao.getAll().map {
        movieMapper.fromDBToMovieListDomain(it)
    }

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        movieDao.insertMovies(movieMapper.fromDomainToMoviesDatabase(movies))
    }.fold(ifLeft = {it}) {
        null
    }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findMovie(id: Int) = movieDao.findByID(id).map {
        movieMapper.fromDBToMovieDomain(it)
    }

    override suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(
        movieMapper.fromDomainToMovieDatabase(movie)
    )
}
