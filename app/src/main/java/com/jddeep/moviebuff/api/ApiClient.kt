package com.jddeep.moviebuff.api

import com.google.gson.GsonBuilder
import com.jddeep.moviebuff.utils.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private var apiClient: Retrofit? = null
    }

    fun getClient(url: String): Retrofit {

        if (apiClient == null) {
            val gson = GsonBuilder().create()

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().url().newBuilder()
                    val queryUrl = requestBuilder.addQueryParameter("api_key", Api.API_KEY).build()
                    val request = chain.request().newBuilder().url(queryUrl).build()
                    chain.proceed(request)
                }
                .build()

            apiClient = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        return apiClient!!
    }
}