package com.gpillaca.upcomingmovies.ui.main

import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) {

    interface View {
        fun showProgress()
        fun updateData(movies: List<Movie>)
        fun hideProgress()
        fun navigateTo(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view
        scope.launch {
            view.showProgress()
            view.updateData(movieRepository.findPopularMovies())
            view.hideProgress()
        }
    }

    fun onMovieClicked(movie: Movie) {
        view?.navigateTo(movie)
    }
}
