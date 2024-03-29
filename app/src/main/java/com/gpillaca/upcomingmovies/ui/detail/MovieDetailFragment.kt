package com.gpillaca.upcomingmovies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.Constants
import com.gpillaca.upcomingmovies.R
import com.gpillaca.upcomingmovies.databinding.FragmentMovieDetailBinding
import com.gpillaca.upcomingmovies.model.database.Movie
import com.gpillaca.upcomingmovies.ui.common.loadUrl
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val safeArgs: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailBinding

    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(safeArgs.movie)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.state.collect { state ->
                    showMovieDetail(state.movie)
                }
            }
        }
    }

    private fun showMovieDetail(movie: Movie) {
        binding.movieDetailToolbar.title = movie.title

        val background = movie.backdropPath ?: movie.posterPath
        binding.movieDetailImage.loadUrl("${BuildConfig.HOST_IMAGE}${Constants.PATH_IMAGE_BACKGROUND}$background")
        binding.movieDetailSummary.text = movie.overview
        binding.movieDetailInfo.setMovieInfo(movie)
    }
}
