package com.gpillaca.upcomingmovies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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
import com.gpillaca.upcomingmovies.data.Movie
import com.gpillaca.upcomingmovies.data.repository.MovieRepository
import com.gpillaca.upcomingmovies.ui.common.loadUrl
import com.gpillaca.upcomingmovies.usecase.FindMovieUseCase
import com.gpillaca.upcomingmovies.usecase.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val safeArgs: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailBinding

    private val movieRepository by lazy {
        MovieRepository(requireActivity().application)
    }

    private val findMovieUseCase by lazy {
        FindMovieUseCase(movieRepository)
    }

    private val switchMovieFavoriteUseCase by lazy {
        SwitchMovieFavoriteUseCase(movieRepository)
    }

    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(safeArgs.movieId, findMovieUseCase, switchMovieFavoriteUseCase)
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
                    state.movie?.let {
                        showMovieDetail(it)
                    }
                }
            }
        }

        binding.movieDetailFavorite.setOnClickListener {
            movieDetailViewModel.onFavoriteClicked()
        }
    }

    private fun showMovieDetail(movie: Movie) {
        binding.movieDetailToolbar.title = movie.title

        val background = movie.backdropPath ?: movie.posterPath
        binding.movieDetailImage.loadUrl("${BuildConfig.HOST_IMAGE}${Constants.PATH_IMAGE_BACKGROUND}$background")
        binding.movieDetailSummary.text = movie.overview
        binding.movieDetailInfo.setMovieInfo(movie)

        val favoriteIcon = if (movie.favorite) {
            R.drawable.ic_favorite_on_24
        } else {
            R.drawable.ic_favorite_off_24
        }

        binding.movieDetailFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), favoriteIcon))
    }
}
