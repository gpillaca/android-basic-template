package com.gpillaca.upcomingmovies.framework.server

import com.gpillaca.upcomingmovies.domain.common.Either
import com.gpillaca.upcomingmovies.domain.mapper.MovieMapper
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import com.gpillaca.upcomingmovies.di.ApiKey
import com.gpillaca.upcomingmovies.domain.common.Error
import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.domain.common.catch
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
