package com.example.trendingmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingmovies.BR
import com.example.trendingmovies.data.model.movieData.Movie
import com.example.trendingmovies.databinding.ViewHolderMovieVerticalBinding

class MovieAdapterVertical: PagingDataAdapter<Movie, MovieAdapterVertical.MyviewHolder>(Diff_Util) {

    companion object{
        val Diff_Util= object :DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID==newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem==newItem
            }

        }
    }
    var onCLick: ((String) -> Unit)? = null

    fun onMovieClick(listener: (String) -> Unit) {
        onCLick = listener
    }
    inner class MyviewHolder(val viewHolderMovieBinding: ViewHolderMovieVerticalBinding):
        RecyclerView.ViewHolder(viewHolderMovieBinding.root){

    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val data = getItem(position)

        holder.viewHolderMovieBinding.setVariable(BR.movie,getItem(position))
        holder.viewHolderMovieBinding.root.setOnClickListener {
            onCLick?.let {
                it(data?.imdbID!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val binding =
            ViewHolderMovieVerticalBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return MyviewHolder(binding)
    }
}
