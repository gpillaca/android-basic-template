package com.gpillaca.upcomingmovies.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gpillaca.upcomingmovies.R
import com.gpillaca.upcomingmovies.databinding.FragmentMainBinding
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.model.MovieRepository
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private val movieRepository by lazy {
        MovieRepository(requireActivity() as AppCompatActivity)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(movieRepository)
    }

    private val adapter = MoviesAdapter{
        mainViewModel.onMovieClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: MainViewModel.UiState) {
        binding.progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(movie: Movie) {
        val navAction = MainFragmentDirections.actionMainToMoviedetail(movie)
        findNavController().navigate(navAction)
    }
}
