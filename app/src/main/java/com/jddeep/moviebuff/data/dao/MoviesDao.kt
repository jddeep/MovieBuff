package com.jddeep.moviebuff.data.dao

import androidx.room.*
import com.jddeep.moviebuff.data.models.MovieData

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies WHERE id=:movieId")
    fun get(movieId: Int): MovieData?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllMovies(movies: List<MovieData>)

    @Delete
    fun removeMovie(movie: MovieData)

    @Query("DELETE FROM movies")
    fun clear()
}