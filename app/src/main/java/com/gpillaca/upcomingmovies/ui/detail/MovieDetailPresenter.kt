package com.gpillaca.upcomingmovies.ui.detail

import com.gpillaca.upcomingmovies.model.Movie

class MovieDetailPresenter {

    interface View {
        fun showMovieDetail(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View, movie: Movie) {
        this.view = view

        view.showMovieDetail(movie)
    }
}
