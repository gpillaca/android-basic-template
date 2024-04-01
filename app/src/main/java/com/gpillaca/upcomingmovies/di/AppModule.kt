package com.gpillaca.upcomingmovies.di

import android.app.Application
import androidx.room.Room
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.data.PermissionChecker
import com.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import com.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import com.gpillaca.upcomingmovies.framework.AndroidPermissionChecker
import com.gpillaca.upcomingmovies.framework.PlayServicesLocationDataSource
import com.gpillaca.upcomingmovies.framework.database.MovieDataBase
import com.gpillaca.upcomingmovies.framework.database.MovieRoomDataSource
import com.gpillaca.upcomingmovies.framework.server.MovieServerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieDataBase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDataBase) = db.movieDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MovieRoomDataSource):  MovieLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MovieServerDataSource): MovieRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker

}
