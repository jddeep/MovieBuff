package com.jddeep.moviebuff.data.cache

import com.jddeep.moviebuff.data.models.MovieData

class MemoryMoviesCache : MoviesCache {

    private val movies: LinkedHashMap<Int, MovieData> = LinkedHashMap()

    override fun isEmpty(): Boolean {
        return movies.isEmpty()
    }

    override fun remove(movieEntity: MovieData) {
        movies.remove(movieEntity.id)
    }

    override fun clear() {
        movies.clear()
    }

    override fun save(movieEntity: MovieData) {
        movies[movieEntity.id] = movieEntity
    }

    override fun saveAll(movieEntities: List<MovieData>) {
        movieEntities.forEach { movieEntity -> this.movies[movieEntity.id] = movieEntity }
    }

    override fun getAll(): List<MovieData> {
        return movies.values.toList()
    }

    override fun get(movieId: Int): MovieData {
        return movies[movieId]!!
    }


}