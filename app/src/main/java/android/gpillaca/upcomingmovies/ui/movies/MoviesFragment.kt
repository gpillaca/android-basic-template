package android.gpillaca.upcomingmovies.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import android.gpillaca.upcomingmovies.R
import android.gpillaca.upcomingmovies.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var binding: FragmentMoviesBinding

    private val moviesViewModel: MoviesViewModel by viewModels()

    private val adapter = MoviesAdapter{ movie ->
        moviesState.onMovieClick(movie.id)
    }

    private lateinit var moviesState: MoviesState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)

        moviesState = buildMoviesState()

        binding.recyclerMovies.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.state.map { it.loading }.distinctUntilChanged().collect{ isLoading ->
                    if (binding.swipeRefresh.isRefreshing && isLoading.not()) {
                        binding.swipeRefresh.isRefreshing = false
                    }

                    if (binding.swipeRefresh.isRefreshing.not()) {
                        binding.progress.isVisible = isLoading
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(moviesViewModel.state) {
                    map { state -> state.movies }.distinctUntilChanged().collect { movies ->
                        movies?.let {
                            adapter.submitList(it)
                        }
                    }
                 }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.state.collect{ error ->
                    error.error?.let {
                        Toast.makeText(requireActivity(), moviesState.errorToString(it), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        moviesState.requestLocationPermissions {
            moviesViewModel.onUiReady()
        }

        binding.swipeRefresh.setOnRefreshListener {
            moviesViewModel.requestPopularMovies()
        }
    }
}
