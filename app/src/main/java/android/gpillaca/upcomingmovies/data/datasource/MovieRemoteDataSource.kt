package android.gpillaca.upcomingmovies.data.datasource

import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.domain.common.Error
import android.gpillaca.upcomingmovies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}
