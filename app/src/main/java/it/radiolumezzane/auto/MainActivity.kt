package it.radiolumezzane.auto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

private const val STREAM_URL = "https://custom-azura1.asurahosting.com/listen/radio_lumezzane/radio.mp3"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val player = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(STREAM_URL))
            prepare()
        }

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RadioLumezzaneHome(
                        onPlay = { player.playWhenReady = true },
                        onStop = { player.pause() }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
fun RadioLumezzaneHome(onPlay: () -> Unit, onStop: () -> Unit) {
    var isPlaying by remember { mutableStateOf(false) }
    val brandBlue = Color(0xFF0A3D91)
    val bg = Color(0xFFF5F7FB)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Radio Lumezzane", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = brandBlue)
        Text("La tua radio, sempre con te.", style = MaterialTheme.typography.bodyLarge)

        Button(
            onClick = {
                if (isPlaying) onStop() else onPlay()
                isPlaying = !isPlaying
            },
            colors = ButtonDefaults.buttonColors(containerColor = brandBlue),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) { Text(if (isPlaying) "Stop" else "Play") }

        Spacer(Modifier.height(8.dp))
        SectionCard("Programmi", "Scopri palinsesto, rubriche e ospiti della settimana.")
        SectionCard("Notizie", "Aggiornamenti locali, eventi e comunicazioni della radio.")
        SectionCard("Contatti", "Email: info@radiolumezzane.it")
    }
}

@Composable
fun SectionCard(title: String, content: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
