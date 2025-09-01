// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition

@Composable
fun ChildScreen(
    navController: NavHostController?,
    title: String,
    isLoading: Boolean = false,
    content: @Composable (ScalingLazyListState) -> Unit
) {
    val scalingLazyListState = rememberScalingLazyListState()

    // Scroll to the top when screen appears
    LaunchedEffect(Unit) {
        scalingLazyListState.scrollToItem(0)
    }

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .safeContentPadding()
                .childScreenPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.caption2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp )
            )

            LoadingImage(isLoading)

            content(scalingLazyListState)
        }
    }
}


@Composable
fun LoadingImage(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
internal fun Modifier.childScreenPadding(
    normalPadding: Dp = 16.dp,
    factor: Float = 0.08f
): Modifier {
    val configuration = LocalConfiguration.current
    val isScreenRound = configuration.isScreenRound
    val density = LocalDensity.current

    return if (isScreenRound) {
        val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
        val insetPx = (factor * screenWidthPx).toInt()
        val insetDp = with(density) { insetPx.toDp() }
        this.padding(insetDp)
    } else {
        this.padding(normalPadding)
    }
}

@Composable
private fun Modifier.safeContentPadding(): Modifier {
    val paddingValues = WindowInsets.safeContent.asPaddingValues()
    return this.padding(paddingValues)
}
