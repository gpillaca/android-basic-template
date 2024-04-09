package android.gpillaca.upcomingmovies.framework.database

import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.domain.mapper.MovieMapper
import android.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import android.gpillaca.upcomingmovies.domain.common.Error
import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.domain.common.catch
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

    override suspend fun save(movies: List<Movie>): Either<Error, Unit> = catch {
        movieDao.insertMovies(movieMapper.fromDomainToMoviesDatabase(movies))
    }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findMovie(id: Int) = movieDao.findByID(id).map {
        movieMapper.fromDBToMovieDomain(it)
    }

    override suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(
        movieMapper.fromDomainToMovieDatabase(movie)
    )
}
