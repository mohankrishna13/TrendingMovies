package com.example.trendingmovies.data.model.movieData

import com.example.trendingmovies.data.model.movieData.Movie

data class MovieResponse(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)
