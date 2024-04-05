package com.gpillaca.upcomingmovies.data.datasource

import com.gpillaca.upcomingmovies.domain.common.Either
import com.gpillaca.upcomingmovies.domain.common.Error
import com.gpillaca.upcomingmovies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun save(movies: List<Movie>): Either<Error, Unit>

    suspend fun isEmpty(): Boolean

    fun findMovie(id: Int): Flow<Movie>

    suspend fun updateMovie(movie: Movie)
}
