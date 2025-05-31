package com.vlad_skoryk.moviemate.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class MovieResponse(
    val results: List<Movie>
)

@Parcelize
data class Movie(
        val id: Int,

        val title: String,

        val overview: String?,

        @SerializedName("release_date")
        val releaseDate: String?,

        @SerializedName("poster_path")
        val posterPath: String?,

        @SerializedName("vote_average")
        val voteAverage: Double,

        @SerializedName("belongs_to_collection")
        val belongsToCollection: BelongsToCollection? // краще зробити окремий клас
) : Parcelable

@Parcelize
data class BelongsToCollection(
        val id: Int,
        val name: String,

        @SerializedName("poster_path")
        val posterPath: String?,

        @SerializedName("backdrop_path")
        val backdropPath: String?
) : Parcelable

