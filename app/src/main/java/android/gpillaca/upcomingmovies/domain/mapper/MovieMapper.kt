package android.gpillaca.upcomingmovies.domain.mapper

import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.framework.database.MovieDB
import android.gpillaca.upcomingmovies.framework.server.MovieResponse

object MovieMapper {

    fun fromResponseToMovieListDomain(movieResponseList: List<MovieResponse>): List<Movie> = movieResponseList.map { movieResponse ->
        fromResponseToMovieDomain(movieResponse)
    }

    private fun fromResponseToMovieDomain(movieResponse: MovieResponse): Movie = with(movieResponse) {
        Movie(
            id,
            title,
            overview,
            releaseDate,
            posterPath,
            backdropPath ?: "",
            originalLanguage,
            originalTitle,
            popularity,
            voteAverage,
            favorite
        )
    }

    fun fromDBToMovieListDomain(movieDBList: List<MovieDB>): List<Movie> = movieDBList.map { movieDB ->
        fromDBToMovieDomain(movieDB)
    }

    fun fromDBToMovieDomain(movieDB: MovieDB): Movie = with(movieDB) {
        Movie(
            id,
            title,
            overview,
            releaseDate,
            posterPath,
            backdropPath,
            originalLanguage,
            originalTitle,
            popularity,
            voteAverage,
            favorite
        )
    }

    fun fromDomainToMoviesDatabase(movieList: List<Movie>): List<MovieDB> {
        return movieList.map { movie ->
            fromDomainToMovieDatabase(movie)
        }
    }

    fun fromDomainToMovieDatabase(movie: Movie): MovieDB = with(movie) {
        MovieDB(
            id,
            title,
            overview,
            releaseDate,
            posterPath,
            backdropPath,
            originalLanguage,
            originalTitle,
            popularity,
            voteAverage,
            favorite
        )
    }
}
