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
import com.gpillaca.upcomingmovies.model.repository.MovieRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
                mainViewModel.state.map { it.loading }.distinctUntilChanged().collect{ isLoading ->
                    binding.progress.isVisible = isLoading
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(mainViewModel.state) {
                    map { state -> state.movies }.distinctUntilChanged().collect { movies ->
                        movies?.let {
                            adapter.submitList(it)
                        }
                    }
                }
            }
        }

        mainState.requestLocationPermissions {
            mainViewModel.onUiReady()
        }
    }
}
