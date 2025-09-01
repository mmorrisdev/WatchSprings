// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.SubcomposeAsyncImage


@Composable
fun ShopDetailScreen(viewModel: ShopViewModel) {
    val selectedItem = viewModel.selectedItem.collectAsState().value

    ChildScreen(
        navController = null,
        title = selectedItem?.displayName ?: "Details",
        isLoading = false
    ) { scalingLazyListState ->

        if (selectedItem == null) {
            Text("No item selected", modifier = Modifier.padding(16.dp))
            return@ChildScreen
        }

        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scalingLazyListState
        ) {
            item {
                ShopImageLayered(selectedItem)
                Spacer(modifier = Modifier.height(8.dp))
            }

            selectedItem.displayType?.let { displayType ->
                item {
                    Text(
                        text = displayType,
                        style = MaterialTheme.typography.caption1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            selectedItem.price?.finalPrice?.let { price ->
                item {
                    Text(
                        text = "$price V-Bucks",
                        style = MaterialTheme.typography.caption2,
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            selectedItem.rarity?.name?.let { rarity ->
                item {
                    Text(
                        text = "Rarity: $rarity",
                        style = MaterialTheme.typography.caption2,
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            selectedItem.displayDescription?.let { desc ->
                item {
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }

            if (!selectedItem.granted.isNullOrEmpty()) {
                item {
                    Text(
                        text = "Includes:",
                        style = MaterialTheme.typography.caption1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }

                // This is now scoped properly within the list
                items(selectedItem.granted) { grantedItem ->
                    GrantedItemCard(grantedItem)
                }
            }
        }
    }
}

@Composable
fun GrantedItemCard(item: ShopGrantedItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        onClick = {} // Optional: tap to inspect granted item in future
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item.images?.icon?.let { iconUrl ->
                SubcomposeAsyncImage(
                    model = iconUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(Color.DarkGray)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.name ?: "Unnamed",
                style = MaterialTheme.typography.caption2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
