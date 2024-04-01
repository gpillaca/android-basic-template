package com.gpillaca.upcomingmovies

import android.app.Application
import androidx.room.Room
import com.gpillaca.upcomingmovies.framework.database.MovieDataBase

class AppUpComingMovies: Application() {

    lateinit var db: MovieDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            MovieDataBase::class.java, "movie-db"
        ).build()
    }
}
