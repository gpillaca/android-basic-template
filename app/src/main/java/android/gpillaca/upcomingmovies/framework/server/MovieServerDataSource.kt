package android.gpillaca.upcomingmovies.framework.server

import android.gpillaca.upcomingmovies.domain.common.Either
import android.gpillaca.upcomingmovies.domain.mapper.MovieMapper
import android.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import android.gpillaca.upcomingmovies.di.ApiKey
import android.gpillaca.upcomingmovies.domain.common.Error
import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.domain.common.catch
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val movieMapper: MovieMapper,
    private val remoteService: RemoteService
) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = catch {
        val movieResponseList = remoteService.listPopularMovies(apiKey, region).results
        movieMapper.fromResponseToMovieListDomain(movieResponseList)
    }

}
