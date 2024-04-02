package com.gpillaca.upcomingmovies.data.datasource

import com.gpillaca.upcomingmovies.Either
import com.gpillaca.upcomingmovies.domain.Error
import com.gpillaca.upcomingmovies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}
