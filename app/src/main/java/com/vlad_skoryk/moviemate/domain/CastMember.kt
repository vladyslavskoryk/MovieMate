package com.vlad_skoryk.moviemate.domain

data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    val job: String?,
    val profilePath: String?
)
