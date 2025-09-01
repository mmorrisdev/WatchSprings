// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

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
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel)
{
    val isLoading by viewModel.isLoading.collectAsState()
    val newsItems = viewModel.newsResponse.collectAsState().value?.news ?: emptyList()

    LaunchedEffect(Unit) {
        viewModel.refreshNews()
    }

    ChildScreen(navController = navController,
                title = "Battle Royale Updates",
                isLoading = isLoading)
    { scalingLazyListState ->

        ScalingLazyColumn(
            state = scalingLazyListState,
            modifier = Modifier.fillMaxSize()
        )  {
            items(newsItems) { news ->
                NewsCard(news) {
                    viewModel.selectNews(news)
                    navController.navigate("newsDetail")
                }
            }
        }
    }
}

@Composable
fun NewsCard(news: News, onClick: () -> Unit) {
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
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = news.image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                loading = {
                    SquareLoadingImage(modifier = Modifier.matchParentSize())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = news.title,
                style = MaterialTheme.typography.title2,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}


