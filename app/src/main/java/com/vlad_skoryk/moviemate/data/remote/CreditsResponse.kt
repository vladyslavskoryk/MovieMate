package com.vlad_skoryk.moviemate.data.remote

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    val cast: List<CastDto>
)

data class CastDto(
    val id: Int,
    val name: String,
    val character: String,
    val job: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)
