package com.example.muzyczka_projekt.data.repository

import com.example.muzyczka_projekt.data.data_source.ArtistsRemoteDataSource
import com.example.muzyczka_projekt.domain.model.Artist
import com.example.muzyczka_projekt.domain.repository.ArtistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Możesz zmienić nazwę klasy na ArtistsRemoteRepository, jeśli chcesz być bardzo dokładny,
// ale najważniejsze, żeby poprawnie implementowała interfejs z domeny.
class ArtistsLocalRepository : ArtistsRepository {

    override fun getAllArtists(): Flow<List<Artist>> = flow {
        // Pobieramy listę z serwera przez Ktor Client
        val remoteArtists = ArtistsRemoteDataSource.fetchAllArtists()
        // Emitujemy pobrane dane do strumienia Flow, który subskrybuje Use Case i ViewModel
        emit(remoteArtists)
    }

    override suspend fun toggleArtistLiked(artistId: String) {
        // Wysyłamy żądanie POST na serwer
        ArtistsRemoteDataSource.toggleArtistLiked(artistId)
    }
}