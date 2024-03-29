package com.gpillaca.upcomingmovies.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gpillaca.upcomingmovies.R
import com.gpillaca.upcomingmovies.databinding.FragmentMainBinding
import com.gpillaca.upcomingmovies.model.MovieRepository
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private val movieRepository by lazy {
        MovieRepository(requireActivity().application)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(movieRepository)
    }

    private val adapter = MoviesAdapter{ movie ->
        mainState.onMovieClick(movie)
    }

    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        mainState = buildMainState()

        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }

        mainState.requestLocationPermissions {
            mainViewModel.onUiReady()
        }
    }

    private fun updateUI(state: MainViewModel.UiState) {
        binding.progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
    }
}
