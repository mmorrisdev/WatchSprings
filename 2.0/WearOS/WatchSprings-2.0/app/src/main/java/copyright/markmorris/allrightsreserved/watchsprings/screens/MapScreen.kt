// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.SubcomposeAsyncImage
import kotlin.math.roundToInt


@Composable
fun MapScreen()
{
    var isLoading by remember { mutableStateOf(true) }

    val isScreenRound = LocalConfiguration.current.isScreenRound
    val fillFraction = if (isScreenRound) 0.8f else 1.0f

    ChildScreen(
        navController = null,
        title = "Current Map",
        isLoading = isLoading
    ) {
        Box(
           modifier = Modifier.fillMaxSize(fraction = fillFraction)
        ) {
            ScrollableMapViewer(onLoadFinished = { isLoading = false })
        }
    }
}

@Composable
fun ScrollableMapViewer(onLoadFinished: () -> Unit) {
    var offsetX by remember { mutableFloatStateOf(-100f) } // center the 2048 map
    var offsetY by remember { mutableFloatStateOf(-200f) }
    var scale by remember { mutableFloatStateOf(1f) }
    var handVisible by remember { mutableStateOf(true) } // 🔥 Track hand visibility
    var isLoaded by remember { mutableStateOf(false) }

    val mapSize = 2048.dp

    Box(
        modifier = Modifier
            .clipToBounds()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    // Hide the hand on first touch
                    if (handVisible) handVisible = false
                    offsetX += pan.x
                    offsetY += pan.y
                    scale = (scale * zoom).coerceIn(0.5f, 4f)
                }
            }
    ) {
        SubcomposeAsyncImage(
            model = MainActivity.fortniteAPI.getMapImageUrl(),
            contentDescription = "Fortnite Map",
            contentScale = ContentScale.None,
            onSuccess = {
                onLoadFinished()
                isLoaded = true
            },
            modifier = Modifier
                .requiredSize(mapSize)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        )

        if (isLoaded && handVisible) {
            Text(
                text = "👆👉",
                style = MaterialTheme.typography.title2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
