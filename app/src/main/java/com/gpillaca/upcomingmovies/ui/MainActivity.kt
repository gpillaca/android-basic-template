package com.gpillaca.upcomingmovies.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gpillaca.upcomingmovies.databinding.ActivityMainBinding
import com.gpillaca.upcomingmovies.model.MovieRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            adapter.submitList(movieRepository.findPopularMovies())
        }
    }
}
