package com.jddeep.moviebuff.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jddeep.moviebuff.R
import com.jddeep.moviebuff.data.models.MovieData
import com.jddeep.moviebuff.utils.Api
import kotlinx.android.synthetic.main.popular_movies_item_row.view.*

class PopularMoviesAdapter constructor(
    private val onMovieSelected:
        (MovieData, View) -> Unit
) :
    RecyclerView.Adapter<PopularMoviesAdapter.MovieCellViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.popular_movies_item_row,
            parent,
            false
        )
        return MovieCellViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, onMovieSelected)
    }

    private val movies: MutableList<MovieData> = mutableListOf()


    override fun getItemCount(): Int {
        return movies.size
    }


    fun addMovies(movies: List<MovieData>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailIv: AppCompatImageView = itemView.thumbnail_iv

        fun bind(movie: MovieData, listener: (MovieData, View) -> Unit) = with(itemView) {

            title.text = movie.originalTitle
            ViewCompat.setTransitionName(thumbnailIv, movie.id.toString())

            movie.posterPath?.let {
                Glide.with(context)
                    .load("${Api.IMAGE_BASE_URL}$it")
                    .into(thumbnailIv)
            }
            setOnClickListener {
                listener(movie, itemView)
            }
        }

    }
}