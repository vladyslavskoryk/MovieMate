package com.vlad_skoryk.moviemate.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue



data class MovieResponse(
    val results: List<Movie>
)

@Parcelize
data class Movie(
        val id: Int,
        val title: String,
        val overview: String?,

        @SerializedName("genre")
        val genre: List<String>?,

        @SerializedName("release_date")
        val releaseDate: String?,

        @SerializedName("poster_path")
        val posterPath: String?,

        @SerializedName("backdrop_path")
        val backdropPath: String?,

        @SerializedName("vote_average")
        val voteAverage: Double?,

        @SerializedName("metascore")
        val metaScore: String?,

        @SerializedName("director")
        val director: String?,

        @SerializedName("belongs_to_collection")
        val belongsToCollection: BelongsToCollection?,

        @SerializedName("videos")
        val videos: @RawValue VideoResponse? = null

) : Parcelable {

        val youtubeTrailerId: String?
                get() = videos?.results
                        ?.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }
                        ?.key
}


        @Parcelize
data class BelongsToCollection(
        val id: Int,
        val name: String,

        @SerializedName("poster_path")
        val posterPath: String?,

        @SerializedName("backdrop_path")
        val backdropPath: String?
) : Parcelable

