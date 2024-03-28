package com.gpillaca.upcomingmovies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.Constants
import com.gpillaca.upcomingmovies.databinding.ActivityMovieDetailBinding
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.ui.common.getParcelableExtraCompat
import com.gpillaca.upcomingmovies.ui.common.loadUrl

class MovieDetailActivity : AppCompatActivity(), MovieDetailPresenter.View {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private lateinit var binding: ActivityMovieDetailBinding

    private val movieDetailPresenter by lazy {
        MovieDetailPresenter()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtraCompat<Movie>(MOVIE) ?: throw IllegalStateException()

        movieDetailPresenter.onCreate(this, movie)
    }

    override fun showMovieDetail(movie: Movie) {
        binding.movieDetailToolbar.title = movie.title

        val background = movie.backdropPath ?: movie.posterPath
        binding.movieDetailImage.loadUrl("${BuildConfig.HOST_IMAGE}${Constants.PATH_IMAGE_BACKGROUND}$background")
        binding.movieDetailSummary.text = movie.overview
        binding.movieDetailInfo.setMovieInfo(movie)
    }
}
