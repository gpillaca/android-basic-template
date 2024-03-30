package com.gpillaca.upcomingmovies.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val favorite: Boolean
)
