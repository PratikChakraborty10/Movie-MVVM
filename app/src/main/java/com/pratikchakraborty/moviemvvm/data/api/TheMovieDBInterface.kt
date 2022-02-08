package com.pratikchakraborty.moviemvvm.data.api

import com.pratikchakraborty.moviemvvm.data.vo.MovieDetails
import com.pratikchakraborty.moviemvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    //    https://api.themoviedb.org/3/movie/popular?api_key=7c089cc11a7cf12ebfe45bde24431e53&page=1
    //    https://api.themoviedb.org/3/movie/739413?api_key=7c089cc11a7cf12ebfe45bde24431e53&language=en-US
    //    https://api.themoviedb.org/3

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int) : Single<MovieDetails>

}