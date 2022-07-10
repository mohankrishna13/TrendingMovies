package com.example.trendingmovies.presentation.ui.homeScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trendingmovies.data.model.movieData.Movie
import com.example.trendingmovies.repository.MovieRepository
import com.example.trendingmovies.util.ApiConstants
import com.example.trendingmovies.util.Status

class HomeScreenPagging(private val s:String,val movieRepository: MovieRepository):PagingSource<Int, Movie>() {
    companion object {
        var apiStatus=MutableLiveData<String>()
        fun cmdInit():MutableLiveData<String>{
            return apiStatus
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val responseResult = movieRepository.getMoviesList(s, page, ApiConstants.API_KEY)
            if(responseResult.status==Status.ERROR){
                apiStatus.postValue(responseResult.message)
            }else if(responseResult.status==Status.SUCCESS){
                if(s.length==0){
                    apiStatus.postValue("Start")
                }else{
                    if(responseResult.message.toString().compareTo("null")==0){
                        apiStatus.postValue("Empty")
                    }else{
                        apiStatus.postValue("Success")
                    }
                }



            }
            LoadResult.Page(
                data = responseResult.data?.Search!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseResult.data?.Search?.isEmpty()!!) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}