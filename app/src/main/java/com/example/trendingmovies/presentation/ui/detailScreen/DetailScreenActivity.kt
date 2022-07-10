package com.example.trendingmovies.presentation.ui.detailScreen

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.trendingmovies.R
import com.example.trendingmovies.databinding.ActivityDetailScreenBinding
import com.example.trendingmovies.presentation.viewModel.DetailsScreenViewModel
import com.example.trendingmovies.util.InternetConnection
import com.example.trendingmovies.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreenActivity : AppCompatActivity() {
    lateinit var  detailScreenActivity: ActivityDetailScreenBinding
    lateinit var progressDialog: ProgressDialog

   lateinit var detailsScreenViewModel:DetailsScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailScreenActivity=DataBindingUtil.setContentView(this,R.layout.activity_detail_screen)
        progressDialog= ProgressDialog(this)
        setSupportActionBar(detailScreenActivity.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        detailScreenActivity.toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })


        detailsScreenViewModel=ViewModelProvider(this).get(DetailsScreenViewModel::class.java)

        if(InternetConnection().isOnline(this)){
            detailsScreenViewModel.getMovieDetails(intent.getStringExtra("movieId").toString())
            progressDialog.setTitle("Feteching Data")
            progressDialog.setMessage("Please Wait..")
            detailsScreenViewModel.seletedmovieDetails.observe(this) {
                when (it.getContentIfNotHandled()?.status) {
                    Status.ERROR -> {
                        progressDialog.dismiss()
                    }
                    Status.SUCCESS -> {
                        progressDialog.dismiss()
                        Glide.with(this).load(it.peekContent().data?.Poster).into(detailScreenActivity.movieImage)
                        detailScreenActivity.selectedMovieDetails = it.peekContent().data
                    }

                }
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}