package com.example.muzyczka_projekt


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muzyczka_projekt.presentation.artistsList.ArtistsListScreen
import com.example.muzyczka_projekt.presentation.artistsList.ArtistsListViewModel

@Composable
@Preview
fun App() {
    val viewModel: ArtistsListViewModel = viewModel { ArtistsListViewModel() }
    MaterialTheme {
        Scaffold(
            containerColor = Color(0xFF121212)
        ) {
            padding ->
            ArtistsListScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                viewModel=viewModel
            )
        }
    }
}