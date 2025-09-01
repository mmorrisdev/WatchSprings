// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.SubcomposeAsyncImage

@Composable
fun ShopScreen(navController: NavHostController, viewModel: ShopViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    val items = viewModel.shopResponse.collectAsState().value?.shop ?: emptyList()

    LaunchedEffect(Unit) {
        viewModel.refreshShop()
    }

    ChildScreen(
        navController = navController,
        title = "Daily Shop",
        isLoading = isLoading
    ) { scalingLazyListState ->

        ScalingLazyColumn(modifier = Modifier.fillMaxSize(), state = scalingLazyListState)
        {
            items(items) { item ->
                ShopCard(item) {
                    viewModel.selectItem(item)
                    navController.navigate("shopDetail")
                }
            }
        }
    }
}

@Composable
fun ShopCard(item: ShopItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShopImageLayered(item)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.displayName!!,
                style = MaterialTheme.typography.title2,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ShopImageLayered(item: ShopItem) {
    val bg = item.displayAssets?.firstOrNull()?.background
    val fg = item.displayAssets?.firstOrNull()?.url

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        bg?.let {
            SubcomposeAsyncImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    SquareLoadingImage(modifier = Modifier.matchParentSize())
                },
                modifier = Modifier.matchParentSize()
            )
        }

        fg?.let {
            SubcomposeAsyncImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    SquareLoadingImage(modifier = Modifier.matchParentSize())
                },
                modifier = Modifier.matchParentSize()
            )
        }
    }
}
