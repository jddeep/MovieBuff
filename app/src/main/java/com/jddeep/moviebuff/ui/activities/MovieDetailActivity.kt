package com.jddeep.moviebuff.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.jddeep.moviebuff.R
import com.jddeep.moviebuff.data.models.DetailsData
import com.jddeep.moviebuff.utils.Api
import com.jddeep.moviebuff.utils.Status
import com.jddeep.moviebuff.viewModels.DetailMovieVM
import kotlinx.android.synthetic.main.activity_movie_detail.*

const val MOVIE_ID = "movie_id"
const val POSTER_PATH = "poster_path"
const val TRANSITION_NAME = "poster"

class MovieDetailActivity : AppCompatActivity() {

    private val detailVM: DetailMovieVM by viewModels()

    private var movieId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
        }
        initObservers()
        initData()
    }

    private fun initData() {
        if (intent.hasExtra(TRANSITION_NAME)) {
            val transition = intent.extras!!.getString(TRANSITION_NAME)!!
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ViewCompat.setTransitionName(poster_image, transition)
            }
        }
        if (intent.hasExtra(POSTER_PATH)) {
            loadImage(intent.extras!!.getString(POSTER_PATH)!!, poster_image)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                startPostponedEnterTransition()
            }
        }
        if (intent.hasExtra(MOVIE_ID)) {
            movieId = intent.extras!!.getInt(MOVIE_ID)
            detailVM.getMovieDetails(movieId)
        }
    }

    private fun initUi(data: DetailsData) {
        loadImage(data.backdropPath, backdrop)

        movie_title.text = data.title
        tagline.text = data.tagline
        date_status.text = data.releaseDate
        rating_tv.text = data.voteAverage.toString()
    }

    private fun loadImage(path: String, view: AppCompatImageView) {
        Glide.with(this)
            .load("${Api.IMAGE_BASE_URL}$path")
            .into(view)
    }

    override fun onResume() {
        super.onResume()
        detail_movie_progress.visibility = View.GONE
    }

    private fun initObservers() {
        detailVM.detailsData.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    detail_movie_progress.visibility = View.GONE
                    it.data?.let { data ->
                        initUi(data)
                    }
                }
                Status.ERROR -> {
                    detail_movie_progress.visibility = View.GONE
                    Toast.makeText(this, "Error loading details!", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    detail_movie_progress.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
        super.onBackPressed()
    }
}