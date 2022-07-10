package com.example.trendingmovies.presentation.ui.homeScreen

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendingmovies.R
import com.example.trendingmovies.databinding.ActivityHomeScreenBinding
import com.example.trendingmovies.presentation.adapter.MovieAdapterHorizontal
import com.example.trendingmovies.presentation.adapter.MovieAdapterVertical
import com.example.trendingmovies.presentation.ui.detailScreen.DetailScreenActivity
import com.example.trendingmovies.util.InternetConnection
import com.example.trendingmovies.presentation.viewModel.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity() {

    lateinit var homeScreenBinding: ActivityHomeScreenBinding
    lateinit var homeScreenViewModel: HomeScreenViewModel
    var movieAdapterVertical = MovieAdapterVertical()
    var movieAdapterHorizontal = MovieAdapterHorizontal()
    lateinit var progressDialog: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        setSupportActionBar(homeScreenBinding.movieToolbar)
        homeScreenViewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)
        setRecyclerView()
        progressDialog = ProgressDialog(this)

        if (InternetConnection().isOnline(this)) {
            homeScreenViewModel.movieList.observe(this@HomeScreenActivity) {
                movieAdapterHorizontal.submitData(lifecycle, it)
                movieAdapterVertical.submitData(lifecycle, it)
            }
        } else {
            showErrorLayout("Please check Internet Connection")
        }

        //Checking Api is Status
        homeScreenViewModel.getApiStatus().observe(this) {
            progressDialog.dismiss()
            if (it.compareTo("Empty") == 0) {
                Toast.makeText(this, "No Data Exist", Toast.LENGTH_SHORT).show()
                homeScreenBinding.ErrorView.visibility = View.GONE
            } else if (it.compareTo("Start") != 0 && it.compareTo("Success")!=0) {
                showErrorLayout(it)
            }
        }

        //OnClick On Recyclerview items
        movieAdapterHorizontal.onMovieClick {
            callIntent(it)
        }
        movieAdapterVertical.onMovieClick {
           callIntent(it)
        }

    }

    private fun showErrorLayout(it: String?) {
        homeScreenBinding.recyclerviewLayout.visibility = View.GONE
        homeScreenBinding.introScreen.visibility = View.VISIBLE
        homeScreenBinding.ErrorView.visibility = View.VISIBLE
        homeScreenBinding.ErrorView.text = "Error: " + it
    }

    private fun callIntent(it: String) {
        if(InternetConnection().isOnline(this)){
            intent= Intent(this,DetailScreenActivity::class.java)
            intent.putExtra("movieId",it)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please check internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchViewItem: MenuItem = menu!!.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(InternetConnection().isOnline(this@HomeScreenActivity)) {
                    query?.let {
                        progressDialog.setTitle("Please Wait")
                        progressDialog.setMessage("Movies are Fetching..")
                        progressDialog.setCanceledOnTouchOutside(false)
                        progressDialog.show()
                        homeScreenBinding.recyclerviewLayout.visibility = View.VISIBLE
                        homeScreenBinding.introScreen.visibility = View.GONE
                        homeScreenViewModel.setSearchQuery(it)
                    }
                }else{
                    return false
                }
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setRecyclerView() {
        homeScreenBinding.movieRecyclerVt.apply {
            adapter=movieAdapterVertical
            layoutManager=GridLayoutManager(this@HomeScreenActivity,2)
        }
        homeScreenBinding.movieRecyclerHt.apply {
            adapter=movieAdapterHorizontal
            layoutManager=LinearLayoutManager(this@HomeScreenActivity, LinearLayoutManager.HORIZONTAL,false)
        }
    }
}

