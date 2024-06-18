package com.example.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidViewConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.videoplayer.ui.theme.VideoPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background
                ) {
                    ExoPlayerView()
                }


            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView() {
    val Example_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaSource = remember(Example_URI) {
        MediaItem.fromUri(Example_URI)
    }
    LaunchedEffect(key1 = mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    AndroidView(factory = { ctx ->
        PlayerView(ctx).apply {
            player = exoPlayer
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        )
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VideoPlayerTheme {
        Greeting("Android")
    }
}