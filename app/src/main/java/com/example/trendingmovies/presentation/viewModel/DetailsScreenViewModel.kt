package com.example.trendingmovies.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.data.model.movieDetails.MovieDetails
import com.example.trendingmovies.repository.MovieRepository
import com.example.trendingmovies.util.Events
import com.example.trendingmovies.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()
    val seletedmovieDetails: LiveData<Events<Result<MovieDetails>>> = movieDetails


    fun getMovieDetails(imdbId: String)=viewModelScope.launch{
        movieDetails.postValue(Events(movieRepository.getMovieDetails(imdbId)))
    }
}



