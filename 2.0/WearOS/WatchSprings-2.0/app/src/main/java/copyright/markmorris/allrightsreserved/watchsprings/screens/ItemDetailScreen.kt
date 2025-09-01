// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun ItemDetailScreen(viewModel: ItemsViewModel) {
    val selectedItem by viewModel.selectedItem.collectAsState()

    ChildScreen(
        navController = null,
        title = selectedItem?.name ?: "Item Detail",
        isLoading = false
    ) { scalingLazyListState ->
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            state = scalingLazyListState
        ) {
            if (selectedItem != null) {
                item {
                    ItemImageLayered(selectedItem!!.images)
                }

                item {
                    Text(
                        text = selectedItem!!.description,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                selectedItem!!.rarity.name.takeIf { it.isNotBlank() }?.let { rarity ->
                    item {
                        Text(
                            text = "Rarity: $rarity",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                selectedItem!!.series?.name?.let { series ->
                    item {
                        Text(
                            text = "Series: $series",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                selectedItem!!.set?.name?.let { setName ->
                    item {
                        Text(
                            text = "Set: $setName",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = "No item selected.",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
