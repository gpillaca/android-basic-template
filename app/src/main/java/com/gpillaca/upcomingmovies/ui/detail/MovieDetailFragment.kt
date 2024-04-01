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
import com.gpillaca.upcomingmovies.AppUpComingMovies
import com.gpillaca.upcomingmovies.BuildConfig
import com.gpillaca.upcomingmovies.ui.common.Constants
import com.gpillaca.upcomingmovies.R
import com.gpillaca.upcomingmovies.databinding.FragmentMovieDetailBinding
import com.gpillaca.upcomingmovies.data.repository.MovieRepository
import com.gpillaca.upcomingmovies.data.repository.RegionRepository
import com.gpillaca.upcomingmovies.domain.Movie
import com.gpillaca.upcomingmovies.framework.AndroidPermissionChecker
import com.gpillaca.upcomingmovies.framework.database.MovieRoomDataSource
import com.gpillaca.upcomingmovies.framework.server.MovieServerDataSource
import com.gpillaca.upcomingmovies.framework.PlayServicesLocationDataSource
import com.gpillaca.upcomingmovies.ui.common.loadUrl
import com.gpillaca.upcomingmovies.usecase.FindMovieUseCase
import com.gpillaca.upcomingmovies.usecase.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val safeArgs: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailBinding

    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        val regionRepository by lazy {
            RegionRepository(
                AndroidPermissionChecker(requireActivity().application),
                PlayServicesLocationDataSource(requireActivity().application)
            )
        }
        val movieDao = (requireActivity().application as AppUpComingMovies).db.movieDao()
        val movieLocalDataSource = MovieRoomDataSource(movieDao)
        val movieRemoteDataSource = MovieServerDataSource(BuildConfig.API_KEY)

        val movieRepository by lazy {
            MovieRepository(regionRepository, movieLocalDataSource, movieRemoteDataSource)
        }

        val findMovieUseCase by lazy {
            FindMovieUseCase(movieRepository)
        }

        val switchMovieFavoriteUseCase by lazy {
            SwitchMovieFavoriteUseCase(movieRepository)
        }
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

        val background = "${BuildConfig.HOST_IMAGE}${Constants.PATH_IMAGE_BACKGROUND}${movie.backdropPath}"
        binding.movieDetailImage.loadUrl(background)
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
