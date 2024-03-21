package com.gpillaca.upcomingmovies.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.gpillaca.upcomingmovies.databinding.ActivityMainBinding
import com.gpillaca.upcomingmovies.model.MovieRepository
import com.gpillaca.upcomingmovies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieRepository by lazy {
        MovieRepository(this)
    }

    private val adapter = MoviesAdapter{
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            binding.progress.isVisible = true
            adapter.submitList(movieRepository.findPopularMovies())
            binding.progress.isGone = true
        }
    }
}
