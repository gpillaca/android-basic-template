package android.gpillaca.upcomingmovies.data.datasource

import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.domain.common.Error
import android.gpillaca.upcomingmovies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun save(movies: List<Movie>): Either<Error, Unit>

    suspend fun isEmpty(): Boolean

    fun findMovie(id: Int): Flow<Movie>

    suspend fun updateMovie(movie: Movie)
}
