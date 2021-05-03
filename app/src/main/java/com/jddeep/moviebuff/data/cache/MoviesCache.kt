package com.jddeep.moviebuff.data.cache

import com.jddeep.moviebuff.data.models.MovieData

interface MoviesCache {

    fun clear()
    fun save(movieEntity: MovieData)
    fun remove(movieEntity: MovieData)
    fun saveAll(movieEntities: List<MovieData>)
    fun getAll(): List<MovieData>
    fun get(movieId: Int): MovieData
    fun isEmpty(): Boolean
}