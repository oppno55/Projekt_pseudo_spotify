package com.example.muzyczka_projekt.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val imageUrl: String,
    var liked: Boolean = false
)