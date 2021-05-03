package com.jddeep.moviebuff.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.jddeep.moviebuff.R
import com.jddeep.moviebuff.data.models.MovieData
import com.jddeep.moviebuff.ui.adapters.PopularMoviesAdapter
import com.jddeep.moviebuff.utils.Status
import com.jddeep.moviebuff.viewModels.PopularVM
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.popular_movies_item_row.*


class MovieListActivity : AppCompatActivity() {

    private val TAG = "MovieListActivity"

    private val popularVM: PopularVM by viewModels()

    lateinit var adapter: PopularMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        initViews()
        popularVM.getPopularMovies()
        initObservers()
    }

    private fun initViews() {
        adapter = PopularMoviesAdapter { movie, _ ->
            navigateToDetailsScreen(movie)
        }
        popular_movies_recyclerview.layoutManager = GridLayoutManager(this, 2)
        popular_movies_recyclerview.adapter = adapter
    }

    private fun initObservers() {
        popularVM.movies.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    popular_movies_progress.visibility = View.GONE
                    retry_button.visibility = View.GONE
                    it.data?.let { movies ->
                        adapter.addMovies(movies)
                    }
                }
                Status.ERROR -> {
                    popular_movies_progress.visibility = View.GONE
                    retry_button.visibility = View.VISIBLE
                    Toast.makeText(this, "Error while loading movies!", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, it.message.toString())
                }
                Status.LOADING -> {
                    popular_movies_progress.visibility = View.VISIBLE
                    retry_button.visibility = View.GONE
                }
            }
        })
    }

    private fun navigateToDetailsScreen(movie: MovieData) {
        val transition = movie.id.toString()
        val detailIntent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra(TRANSITION_NAME, transition)
            putExtra(MOVIE_ID, movie.id)
            putExtra(POSTER_PATH, movie.posterPath)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            (thumbnail_iv as View?)!!, transition
        )
        startActivity(detailIntent, options.toBundle())
    }

    fun startRetry(view: View) {
        popularVM.getPopularMovies()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}