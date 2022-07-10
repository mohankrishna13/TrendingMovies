package com.example.trendingmovies.presentation.viewModel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.example.trendingmovies.presentation.ui.homeScreen.HomeScreenActivity
import com.example.trendingmovies.repository.MovieRepository
import com.example.trendingmovies.presentation.ui.homeScreen.HomeScreenPagging
import com.example.trendingmovies.util.InternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private val searchQuery=MutableLiveData<String>("")
    val movieList=searchQuery.switchMap{searchQuery->
           Pager(PagingConfig(pageSize = 10)){
               HomeScreenPagging(searchQuery,movieRepository)
           }.liveData.cachedIn(viewModelScope)
    }
    fun getApiStatus():MutableLiveData<String> {
        return HomeScreenPagging.cmdInit()
    }

    fun setSearchQuery(s:String){
            searchQuery.postValue(s)
    }
}
