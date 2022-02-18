package com.pratikchakraborty.moviemvvm.ui.popular_movie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.icu.text.IDNA
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.system.Os.close
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.pratikchakraborty.moviemvvm.R
import com.pratikchakraborty.moviemvvm.data.api.TheMovieDBClient
import com.pratikchakraborty.moviemvvm.data.api.TheMovieDBInterface
import com.pratikchakraborty.moviemvvm.data.repository.NetworkState
import com.pratikchakraborty.moviemvvm.ui.series.SeriesActivity
import com.pratikchakraborty.moviemvvm.ui.single_movie_details.SingleMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    lateinit var movieRepository: MoviePageListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)




        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MoviePageListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        };

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }
    private fun getViewModel() : HomeViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(movieRepository) as T
            }
        })[HomeViewModel::class.java]
    }

    fun openHome(view: View) {
        // Do Nothing
    }
    fun openSeries(view: View) {
        val intent = Intent(this, SeriesActivity::class.java)
        startActivity(intent)
    }
    fun openShare(view: View) {
        // Do Nothing
        val url_download = "https://bit.ly/3LUP4A7"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Download MovieMania - All in One Space for Movie Lovers\n$url_download")
        val chooser = Intent.createChooser(intent, "Share using...")
        startActivity(chooser)
    }
    fun openInfo(view: View) {
        // Do Nothing
        val url = "https://www.example.com/"
        val builder = CustomTabsIntent.Builder()
        val CustomTabsIntent = builder.build()
        CustomTabsIntent.launchUrl(this, Uri.parse(url))
    }
}