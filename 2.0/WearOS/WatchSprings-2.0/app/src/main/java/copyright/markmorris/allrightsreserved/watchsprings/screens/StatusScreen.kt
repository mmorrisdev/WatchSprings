// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun StatusScreen(navController: NavHostController, viewModel: StatusViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    val statusItems by viewModel.fortniteComponents.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshStatus()
    }

    ChildScreen(
        navController = navController,
        title = "Server Status",
        isLoading = isLoading
    ) { scalingLazyListState ->

        ScalingLazyColumn(modifier = Modifier.fillMaxSize(), state = scalingLazyListState)
        {
            items(statusItems) { component ->
                StatusCard(component)
            }
        }
    }
}

@Composable
fun StatusCard(component: StatusComponent) {
    Card(
        onClick = { /* No action needed for now */ },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = component.name,
                style = MaterialTheme.typography.caption2,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )

            StatusDot(component.status)
        }
    }
}

@Composable
fun StatusDot(status: String) {
    val color = if (status == "operational") Color(0xFF4CAF50) else Color.Red

    Box(
        modifier = Modifier
            .size(10.dp)
            .background(color, shape = CircleShape)
    )
}
