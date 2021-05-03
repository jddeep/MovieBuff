package com.jddeep.moviebuff.utils

object DATABASE {
    const val DATABASE_MOVIE = "movies.db"
    const val DATABASE_MOVIE_VERSION = 1
    const val TABLE_MOVIE = "movie"

    const val SELECT_MOVIE = "SELECT * FROM $TABLE_MOVIE ORDER BY popularity DESC, title ASC"

    const val PAGE_SIZE = 20
}

object Api {

     const val BASE_SERVER_URL = "https://api.themoviedb.org/3/"
     const val API_KEY = "cd9b889926234bf87876ba0077312a4e"

     const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
}