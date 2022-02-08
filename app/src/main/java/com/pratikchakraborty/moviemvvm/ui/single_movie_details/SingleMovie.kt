package com.pratikchakraborty.moviemvvm.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pratikchakraborty.moviemvvm.R
import com.pratikchakraborty.moviemvvm.data.api.COVER_BASE_URL
import com.pratikchakraborty.moviemvvm.data.api.POSTER_BASE_URL
import com.pratikchakraborty.moviemvvm.data.api.TheMovieDBClient
import com.pratikchakraborty.moviemvvm.data.api.TheMovieDBInterface
import com.pratikchakraborty.moviemvvm.data.repository.NetworkState
import com.pratikchakraborty.moviemvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUi(it: MovieDetails) {
        movie_name.text = it.title
        release_date.text = it.releaseDate
        overview_txt.text = it.overview
        status_tv.text = it.status
        //budget_tv.text = it.budget.toString()
        rating_tv.text = it.rating.toString()
        vote_count_tv.text = it.voteCount.toString()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        budget_tv.text = formatCurrency.format(it.budget)

        val movieCoverURL = COVER_BASE_URL + it.backdropPath
        Glide.with(this)
            .load(movieCoverURL)
            .into(cover_image)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(poster_image)

    }
    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}