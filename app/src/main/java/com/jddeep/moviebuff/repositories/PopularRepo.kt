package com.jddeep.moviebuff.repositories

import com.jddeep.moviebuff.api.ApiClient
import com.jddeep.moviebuff.api.ApiInterface
import com.jddeep.moviebuff.utils.Api

class PopularRepo {

    private val api = ApiClient().getClient(Api.BASE_SERVER_URL).create(ApiInterface::class.java)

    suspend fun getPopularMovies() = api.getPopularMovies()
}