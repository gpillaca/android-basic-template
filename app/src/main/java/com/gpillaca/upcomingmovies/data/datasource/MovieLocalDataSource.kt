package com.gpillaca.upcomingmovies.data.datasource

import com.gpillaca.upcomingmovies.framework.database.MovieDB
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<MovieDB>>

    suspend fun save(movies: List<MovieDB>)

    suspend fun isEmpty(): Boolean
    fun findMovie(id: Int): Flow<MovieDB>

    suspend fun updateMovie(movie: MovieDB)
}
