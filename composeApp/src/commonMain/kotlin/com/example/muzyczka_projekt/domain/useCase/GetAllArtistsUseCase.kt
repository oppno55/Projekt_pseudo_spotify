package com.example.muzyczka_projekt.domain.useCase

import com.example.muzyczka_projekt.domain.model.Artist
import com.example.muzyczka_projekt.domain.repository.ArtistsRepository
import kotlinx.coroutines.flow.Flow

class GetAllArtistsUseCase(private val repository: ArtistsRepository) {
    operator fun invoke(): Flow<List<Artist>> = repository.getAllArtists()
}