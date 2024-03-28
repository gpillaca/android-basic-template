package com.gpillaca.upcomingmovies.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.gpillaca.upcomingmovies.databinding.ActivityMainBinding
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.MovieRepository
import com.gpillaca.upcomingmovies.ui.detail.MovieDetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var binding: ActivityMainBinding

    private val movieRepository by lazy {
        MovieRepository(this)
    }

    private val mainPresenter by lazy {
        MainPresenter(movieRepository, lifecycleScope)
    }

    private val adapter = MoviesAdapter{
        mainPresenter.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        mainPresenter.onCreate(this)
    }

    override fun showProgress() {
        binding.progress.isVisible = true
    }

    override fun updateData(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    override fun hideProgress() {
        binding.progress.isGone = true
    }

    override fun navigateTo(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie)
        startActivity(intent)
    }
}
