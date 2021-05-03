package com.jddeep.moviebuff.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jddeep.moviebuff.data.dao.MoviesDao
import com.jddeep.moviebuff.data.models.MovieData
import com.jddeep.moviebuff.utils.DATABASE.DATABASE_MOVIE_VERSION

@Database(entities = [MovieData::class], version = DATABASE_MOVIE_VERSION, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}
