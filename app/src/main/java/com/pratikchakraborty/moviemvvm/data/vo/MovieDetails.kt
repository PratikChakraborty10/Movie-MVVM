package com.pratikchakraborty.moviemvvm.data.vo


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val budget: Int?,
    val id: Int?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)