package com.gpillaca.upcomingmovies.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findByID(id: Int): Flow<Movie>

    @Query("SELECT COUNT(id) FROM Movie")
    suspend fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Update
    suspend fun updateMovie(movie: Movie)

}
