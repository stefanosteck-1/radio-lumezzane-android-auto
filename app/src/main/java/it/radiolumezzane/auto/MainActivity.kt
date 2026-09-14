package it.radiolumezzane.auto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

private const val STREAM_URL = "https://custom-azura1.asurahosting.com/listen/radio_lumezzane/radio.mp3"
private const val SITE_URL = "https://stefanovillani.it/radio-lumezzane/"
private const val MAIL_TO = "mailto:info@radiolumezzane.it"
private const val WHATSAPP_URL = "https://wa.me/393000000000"

class MainActivity : ComponentActivity() {

    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(STREAM_URL))
            prepare()
        }

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF2F7DFF),
                    secondary = Color(0xFF79A9FF),
                    background = Color(0xFF0E1117),
                    surface = Color(0xFF151A22),
                    onPrimary = Color.White,
                    onBackground = Color(0xFFEAF0FF),
                    onSurface = Color(0xFFEAF0FF)
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RadioLumezzaneScreen(
                        onPlay = { player.playWhenReady = true },
                        onStop = { player.pause() },
                        onOpenSite = { openUrl(SITE_URL) },
                        onOpenMail = { openUrl(MAIL_TO) },
                        onOpenWhatsApp = { openUrl(WHATSAPP_URL) }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

@Composable
private fun RadioLumezzaneScreen(
    onPlay: () -> Unit,
    onStop: () -> Unit,
    onOpenSite: () -> Unit,
    onOpenMail: () -> Unit,
    onOpenWhatsApp: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E1117))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HeroCard(
            title = "Radio Lumezzane",
            subtitle = "La tua radio sempre con te",
            isPlaying = isPlaying,
            onTogglePlay = {
                if (isPlaying) onStop() else onPlay()
                isPlaying = !isPlaying
            }
        )

        ActionRow(
            onOpenSite = onOpenSite,
            onOpenMail = onOpenMail,
            onOpenWhatsApp = onOpenWhatsApp
        )

        InfoCard(
            title = "Programmi",
            content = "Palinsesto locale, rubriche e ospiti. Musica, territorio e community."
        )

        InfoCard(
            title = "Notizie",
            content = "Aggiornamenti su eventi, iniziative e comunicazioni di Radio Lumezzane."
        )

        InfoCard(
            title = "Contatti",
            content = "Email: info@radiolumezzane.it\nSito: stefanovillani.it/radio-lumezzane"
        )
    }
}

@Composable
private fun HeroCard(
    title: String,
    subtitle: String,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1B2A4B), Color(0xFF2F7DFF))
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(gradient)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE5EEFF)
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = onTogglePlay) {
                Text(if (isPlaying) "■ Stop Radio" else "▶ Play Radio")
            }
        }
    }
}

@Composable
private fun ActionRow(
    onOpenSite: () -> Unit,
    onOpenMail: () -> Unit,
    onOpenWhatsApp: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickAction("Sito", onOpenSite, Modifier.weight(1f))
        QuickAction("Email", onOpenMail, Modifier.weight(1f))
        QuickAction("WhatsApp", onOpenWhatsApp, Modifier.weight(1f))
    }
}

@Composable
private fun QuickAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(52.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF151A22),
        tonalElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, fontWeight = FontWeight.SemiBold, color = Color(0xFFEAF0FF))
        }
    }
}

@Composable
private fun InfoCard(title: String, content: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF151A22),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD9E5FF)
            )
        }
    }
}
