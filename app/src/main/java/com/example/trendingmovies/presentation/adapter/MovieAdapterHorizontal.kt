package com.example.trendingmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingmovies.BR
import com.example.trendingmovies.data.model.movieData.Movie
import com.example.trendingmovies.databinding.ViewHolderMovieHorizontalBinding

class MovieAdapterHorizontal: PagingDataAdapter<Movie, MovieAdapterHorizontal.MyviewHolderHorizontal>(Diff_Util) {

    companion object{
        val Diff_Util= object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID==newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem==newItem
            }

        }
    }
    inner class MyviewHolderHorizontal(val viewHolderMovieHorizontalBinding:ViewHolderMovieHorizontalBinding):
        RecyclerView.ViewHolder(viewHolderMovieHorizontalBinding.root){

    }
    var onCLick: ((String) -> Unit)? = null

    fun onMovieClick(listener: (String) -> Unit) {
        onCLick = listener
    }
    override fun onBindViewHolder(holder: MyviewHolderHorizontal, position: Int) {
        val data = getItem(position)

        holder.viewHolderMovieHorizontalBinding.setVariable(BR.movie,getItem(position))
        holder.viewHolderMovieHorizontalBinding.root.setOnClickListener {
            onCLick?.let {
                it(data?.imdbID!!)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolderHorizontal {
        val binding =
            ViewHolderMovieHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return MyviewHolderHorizontal(binding)
    }



}