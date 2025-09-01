// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition

@Composable
fun MainScreen(navController: NavController, viewModel: StatusViewModel)
{
    val buttonLabels = listOf(
        "Battle Royale Updates" to "updates",
        "Upcoming Items" to "items",
        "Daily Shop" to "shop",
        "Battle Pass Rewards" to "rewards",
        "Current Map" to "map",
        "About" to "about"
    )

    // Remember the ScalingLazyListState
    val scalingLazyListState = rememberScalingLazyListState()

    // Scroll to the top when this Composable appears
    LaunchedEffect(Unit) {
        scalingLazyListState.scrollToItem(0)
    }

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            ScalingLazyColumn(
                state = scalingLazyListState, // Pass the ScalingLazyListState
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Title Section
                item {
                    Text(
                        text = "WatchSprings",
                        style = MaterialTheme.typography.title3, // slightly smaller
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Center,
                        maxLines = 1, // <-- critical!
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }

                item {
                    TitleImage()
                }

                item {
                    Text(
                        text = "Fortnite Server Status:",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    ServerStatusPill(
                        viewModel = viewModel,
                        onClick = { navController.navigate("status") }
                    )
                }

                items(buttonLabels) { (label, route) ->
                    Chip(
                        onClick = { navController.navigate(route) },
                        label = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
    }
}

@Composable
fun TitleImage() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }

}

@Composable
fun ServerStatusPill(
    viewModel: StatusViewModel,
    onClick: () -> Unit
) {
    val isOnline by viewModel.isFortniteOnline.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshStatus()
    }

    val backgroundColor = when {
        isLoading -> MaterialTheme.colors.secondaryVariant
        isOnline -> MaterialTheme.colors.secondary
        else -> MaterialTheme.colors.error
    }

    val label = when {
        isLoading -> "Getting status..."
        isOnline -> "Fortnite is up..."
        else -> "Fortnite is down..."
    }

    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(percent = 50))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.caption2
        )
    }
}
