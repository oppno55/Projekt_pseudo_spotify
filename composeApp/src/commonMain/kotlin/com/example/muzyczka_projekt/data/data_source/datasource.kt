package com.example.muzyczka_projekt.data.data_source

import com.example.muzyczka_projekt.domain.model.Artist
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest // 🔥 DODANY IMPORT
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.header // 🔥 DODANY IMPORT
import io.ktor.serialization.kotlinx.json.json

object ArtistsRemoteDataSource {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(DefaultRequest) {
            header("X-API-Key", "MojeTajneHaslo123")
        }
    }

    private const val BASE_URL = "http://10.67.14.16:8080/api"

    suspend fun fetchAllArtists(): List<Artist> {
        return client.get("$BASE_URL/artists").body()
    }

    suspend fun toggleArtistLiked(artistId: String) {
        client.post("$BASE_URL/artists/$artistId/toggle-liked")
    }
}