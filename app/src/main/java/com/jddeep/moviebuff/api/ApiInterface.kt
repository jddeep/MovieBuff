package com.jddeep.moviebuff.api

import com.jddeep.moviebuff.data.models.DetailsData
import com.jddeep.moviebuff.data.models.MovieListResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
        @GET("movie/{id}?append_to_response=videos,reviews")
        suspend fun getMovieDetails(@Path("id") movieId: Int): DetailsData

        @GET("movie/popular")
        suspend fun getPopularMovies(): MovieListResult
}