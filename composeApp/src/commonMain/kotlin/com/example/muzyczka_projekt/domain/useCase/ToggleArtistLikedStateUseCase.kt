package com.example.muzyczka_projekt.domain.useCase

import com.example.muzyczka_projekt.domain.repository.ArtistsRepository

class ToggleArtistLikedStateUseCase(private val repository: ArtistsRepository){
    suspend operator fun invoke(artistId: String){
        repository.toggleArtistLiked(artistId)
    }
}