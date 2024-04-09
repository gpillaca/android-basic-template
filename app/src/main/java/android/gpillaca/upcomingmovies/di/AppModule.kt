package android.gpillaca.upcomingmovies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import android.gpillaca.upcomingmovies.BuildConfig
import android.gpillaca.upcomingmovies.domain.mapper.MovieMapper
import android.gpillaca.upcomingmovies.data.PermissionChecker
import android.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import android.gpillaca.upcomingmovies.data.datasource.MovieLocalDataSource
import android.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import android.gpillaca.upcomingmovies.framework.AndroidPermissionChecker
import android.gpillaca.upcomingmovies.framework.PlayServicesLocationDataSource
import android.gpillaca.upcomingmovies.framework.database.MovieDataBase
import android.gpillaca.upcomingmovies.framework.database.MovieRoomDataSource
import android.gpillaca.upcomingmovies.framework.server.MovieServerDataSource
import android.gpillaca.upcomingmovies.framework.server.RemoteService
import android.gpillaca.upcomingmovies.ui.common.AppInternetConnectionChecker
import android.gpillaca.upcomingmovies.ui.common.InternetConnectionChecker
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    @ApiUrl
    fun provideApiUrl(): String = BuildConfig.HOST

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

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMovieMapper() = MovieMapper

    @Provides
    fun provideInternetConnectionChecker(@ApplicationContext appContext: Context): InternetConnectionChecker {
        return AppInternetConnectionChecker(context = appContext)
    }

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
