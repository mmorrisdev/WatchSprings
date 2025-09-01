// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.SubcomposeAsyncImage

@Composable
fun BattlePassScreen(navController: NavHostController, viewModel: BattlePassViewModel)
{
    val isLoading by viewModel.isLoading.collectAsState()
    val battlePass = viewModel.battlePassResponse.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.refreshBattlePass()
    }

    ChildScreen(
        navController = navController,
        title = "Battle Pass",
        isLoading = isLoading
    ) { scalingLazyListState ->

        if (battlePass == null) return@ChildScreen

        val displayInfo = battlePass.displayInfo
        val seasonDates = battlePass.seasonDates
        val trailerUrl = battlePass.videos?.firstOrNull()?.url

        ScalingLazyColumn(state = scalingLazyListState, modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    displayInfo?.chapterSeason?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.caption1,
                            color = MaterialTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }

                    displayInfo?.battlepassName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.caption2,
                            color = MaterialTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            if (!trailerUrl.isNullOrBlank()) {
                item {
                    Chip(
                        onClick = {
                            viewModel.selectTrailer(trailerUrl)
                            navController.navigate("battlepass_trailer")
                        },
                        label = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "View Trailer",
                                    color = MaterialTheme.colors.onPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        colors = ChipDefaults.secondaryChipColors(
                            backgroundColor = MaterialTheme.colors.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }


            items(battlePass.rewards.orEmpty()) { reward ->
                reward.item?.let { item ->
                    BattlePassRewardCard(item = item) { }
                }
            }
        }
    }
}

@Composable
fun BattlePassRewardCard(
    item: BattlePassItem,
    onClick: () -> Unit
) {
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
            item.images?.let {
                BattlePassImageLayered(it)
            }

            Spacer(modifier = Modifier.height(4.dp))

            item.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.caption2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }
    }
}

@Composable
fun BattlePassImageLayered(images: ItemImages) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        images.icon_background?.let { bg ->
            SubcomposeAsyncImage(
                model = bg,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    SquareLoadingImage(modifier = Modifier.matchParentSize())
                },
                modifier = Modifier.matchParentSize()
            )
        }

        images.icon?.let { fg ->
            SubcomposeAsyncImage(
                model = fg,
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

@Composable
fun SquareLoadingImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.squareloading),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

