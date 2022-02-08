package com.pratikchakraborty.moviemvvm.data.api

import com.pratikchakraborty.moviemvvm.data.vo.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBInterface {

    //    https://api.themoviedb.org/3/movie/popular?api_key=7c089cc11a7cf12ebfe45bde24431e53
    //    https://api.themoviedb.org/3/movie/739413?api_key=7c089cc11a7cf12ebfe45bde24431e53&language=en-US
    //    https://api.themoviedb.org/3

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int) : Single<MovieDetails>

}