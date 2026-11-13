package com.example.muzyczka_projekt.presentation.artistsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muzyczka_projekt.domain.model.Artist
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import muzyczka_projekt.composeapp.generated.resources.Liked_False
import muzyczka_projekt.composeapp.generated.resources.Liked_True
import muzyczka_projekt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource


@Composable
fun ArtistItem(artist: Artist, onToggleArtistLikedButtonClick: (Artist) -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.TopEnd
        ) {

            KamelImage(
                resource = asyncPainterResource(data =artist.imageUrl),
                contentDescription = "Zdjęcie wykonawcy ${artist.name}",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentScale = ContentScale.Crop,

                onLoading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.Green)
                    }
                },
                onFailure = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("", color = Color.Gray, fontSize = 24.sp)
                    }
                }
            )

            val likedButtonRes = if (artist.liked) Res.drawable.Liked_True else Res.drawable.Liked_False

            Image(
                painter = painterResource(likedButtonRes),
                contentDescription = "Polub artystę",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1E1E1E).copy(alpha = 0.7f))
                    .padding(4.dp)
                    .clickable { onToggleArtistLikedButtonClick(artist) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nazwa artysty
        Text(
            text = artist.name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = artist.genre,
            color = Color.Gray,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}