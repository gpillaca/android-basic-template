package com.gpillaca.upcomingmovies.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gpillaca.upcomingmovies.databinding.ActivityMainBinding
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.MovieRepository
import com.gpillaca.upcomingmovies.ui.detail.MovieDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieRepository by lazy {
        MovieRepository(this)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(movieRepository)
    }

    private val adapter = MoviesAdapter{
        mainViewModel.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        mainViewModel.state.observe(this) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: MainViewModel.UiState) {
        binding.progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie)
        startActivity(intent)
    }
}
