package com.jddeep.moviebuff.data.models

data class ReviewData(
        var id: String,
        var author: String,
        var content: String? = null
)