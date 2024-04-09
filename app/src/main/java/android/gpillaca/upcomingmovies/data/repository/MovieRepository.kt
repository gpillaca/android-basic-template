package android.gpillaca.upcomingmovies.data.repository

import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.domain.common.catch
import android.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import android.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import android.gpillaca.upcomingmovies.domain.common.Error
import android.gpillaca.upcomingmovies.domain.common.left
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

    suspend fun requestPopularMovies(): Either<Error?, Unit> {
        movieRemoteDataSource
            .findPopularMovies(regionRepository.findLastRegion())
            .onRight { movies ->
                saveLocalMovies(movies)
            }
            .onLeft { error ->
                 return error.left()
            }

        return Either.Left(null)
    }

    private suspend fun saveLocalMovies(movies: List<Movie>): Either<Error?, Unit> =
        movieLocalDataSource.save(movies).onLeft { error ->
            return error.left()
        }

    fun findMovie(id: Int) = movieLocalDataSource.findMovie(id)

    suspend fun switchFavorite(movie: Movie) = catch {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        movieLocalDataSource.updateMovie(updatedMovie)
    }
}
