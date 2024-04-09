package android.gpillaca.upcomingmovies.ui.movies

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.gpillaca.upcomingmovies.BuildConfig
import android.gpillaca.upcomingmovies.ui.common.Constants
import android.gpillaca.upcomingmovies.R
import android.gpillaca.upcomingmovies.databinding.ViewMovieBinding
import android.gpillaca.upcomingmovies.domain.Movie
import android.gpillaca.upcomingmovies.ui.common.basicDiffUtil
import android.gpillaca.upcomingmovies.ui.common.inflate
import android.gpillaca.upcomingmovies.ui.common.loadUrl

class MoviesAdapter(
    private val listener: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewMovieBinding.bind(view)
        fun bind(movie: Movie) = with(binding) {
            favoriteIcon.isVisible = movie.favorite
            movieTitle.text = movie.title
            movieCover.loadUrl("${BuildConfig.HOST_IMAGE}${Constants.PATH_IMAGE_POSTER}${movie.posterPath}")
        }
    }
}
