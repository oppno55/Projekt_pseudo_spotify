package com.example.muzyczka_projekt.presentation.artistsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArtistsListScreen(modifier: Modifier, viewModel: ArtistsListViewModel) {
    // Obserwujemy zarówno przefiltrowaną listę, jak i wpisany tekst
    val artistState by viewModel.artists.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = modifier) {

        // Elegancki, ciemny pasek wyszukiwania w stylu Spotify
        TextField(
            value = searchQuery,
            onValueChange = { newText -> viewModel.onSearchQueryChanged(newText) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = { Text("Szukaj wykonawcy lub gatunku...", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF242424),
                unfocusedContainerColor = Color(0xFF242424),
                disabledContainerColor = Color(0xFF242424),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent, // Ukrywa domyślną kreskę pod spodem
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Nasza siatka (wyświetla automatycznie przefiltrowane dane)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier = Modifier.weight(1f), // Zajmuje pozostałą przestrzeń ekranu
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(artistState) { artist ->
                ArtistItem(
                    artist = artist,
                    onToggleArtistLikedButtonClick = {
                        viewModel.handleToggleArtistLikedButtonClick(artist)
                    }
                )
            }
        }
    }
}