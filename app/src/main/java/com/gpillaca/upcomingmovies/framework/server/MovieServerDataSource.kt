package com.gpillaca.upcomingmovies.framework.server

import com.gpillaca.upcomingmovies.MovieMapper
import com.gpillaca.upcomingmovies.data.datasource.MovieRemoteDataSource
import com.gpillaca.upcomingmovies.di.ApiKey
import com.gpillaca.upcomingmovies.domain.Movie
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val movieMapper: MovieMapper,
    private val remoteService: RemoteService
) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): List<Movie> {
        val movieResponseList = remoteService.listPopularMovies(apiKey, region).results
        return movieMapper.fromResponseToMovieListDomain(movieResponseList)
    }

}
