package com.example.muzyczka_projekt.domain.repository

import com.example.muzyczka_projekt.domain.model.Artist
import kotlinx.coroutines.flow.Flow


interface  ArtistsRepository{
    fun getAllArtists(): Flow<List<Artist>>
    suspend fun toggleArtistLiked(artistId: String)
}