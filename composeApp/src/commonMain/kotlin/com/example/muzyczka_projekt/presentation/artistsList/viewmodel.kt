package com.example.muzyczka_projekt.presentation.artistsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzyczka_projekt.data.repository.ArtistsLocalRepository
import com.example.muzyczka_projekt.domain.model.Artist
import com.example.muzyczka_projekt.domain.useCase.GetAllArtistsUseCase
import com.example.muzyczka_projekt.domain.useCase.ToggleArtistLikedStateUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce // 🔥 POTRZEBNY IMPORT
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class) // Wymagane przez Kotlin dla operatora debounce
class ArtistsListViewModel : ViewModel() {

    private val repository = ArtistsLocalRepository()
    private val getAllArtistsUseCase = GetAllArtistsUseCase(repository = repository)
    private val toggleArtistLikedStateUseCase = ToggleArtistLikedStateUseCase(repository = repository)

    // Główna, pełna lista pobrana z serwera
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Publiczny strumień przefiltrowanej listy, który obserwuje widok
    private val _filteredArtists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _filteredArtists.asStateFlow()

    init {
        refreshArtists()

        _searchQuery
            .debounce(300)
            .onEach { query ->
                updateFilteredList(query)
            }
            .launchIn(viewModelScope)
    }

    private fun refreshArtists() {
        viewModelScope.launch {
            try {
                val updatedList = getAllArtistsUseCase().first()
                _artists.value = updatedList
                updateFilteredList(_searchQuery.value) // Aktualizuj listę po pobraniu z sieci
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Ta funkcja wykonuje się natychmiast przy każdym kliknięciu klawisza, aby UI działało płynnie
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    // Logika filtrowania uruchamiana dopiero przez potok debounce
    private fun updateFilteredList(rawQuery: String) {
        val query = rawQuery.trim().lowercase()

        if (query.isEmpty()) {
            _filteredArtists.value = _artists.value
        } else {
            _filteredArtists.value = _artists.value.filter { artist ->
                val matchesText = artist.name.contains(query, ignoreCase = true) ||
                        artist.genre.contains(query, ignoreCase = true)

                val matchesLiked = (query == "liked" || query == "polubione") && artist.liked

                matchesText || matchesLiked
            }
        }
    }

    fun handleToggleArtistLikedButtonClick(artist: Artist) {
        viewModelScope.launch {
            try {
                toggleArtistLikedStateUseCase(artist.id)
                refreshArtists()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}