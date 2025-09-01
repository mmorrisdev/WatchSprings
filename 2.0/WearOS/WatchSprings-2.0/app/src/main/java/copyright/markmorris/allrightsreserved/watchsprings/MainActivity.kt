// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import copyright.markmorris.allrightsreserved.watchsprings.theme.WearAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        instance = this

        fortniteAPI = FortniteApiClient()

        setContent {
            WearApp()
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------

    fun CMDLOG()
    {
        val ste = Thread.currentThread().stackTrace
        Log.d("CMDLOG", "On thread: " + Thread.currentThread().name)
        Log.d("CMDLOG", ste[3].className + " " + ste[3].methodName)
    }

    //--------------------------------------------------------------------------------------------------------------------------------------

    companion object
    {
        lateinit var instance: MainActivity
        lateinit var fortniteAPI: FortniteApiClient
    }
}

//----------------------------------------------------------------------------------------------------------------------------------------

@Composable
fun WearApp()
{
    val navController = rememberSwipeDismissableNavController()

    val newsViewModel: NewsViewModel = viewModel()
    val itemsViewModel: ItemsViewModel = viewModel()
    val shopViewModel: ShopViewModel = viewModel()
    val battlepassViewModel: BattlePassViewModel = viewModel()
    val statusViewModel: StatusViewModel = viewModel()

    WearAppTheme {

            SwipeDismissableNavHost(navController = navController, startDestination = "home")
            {
                composable("home") { MainScreen(navController, viewModel = statusViewModel) }
                    composable("status") { StatusScreen(navController, viewModel = statusViewModel) }

                composable("updates") { NewsScreen(navController = navController, viewModel = newsViewModel) }
                    composable("newsDetail") { NewsDetailScreen(viewModel = newsViewModel) }

                composable("items") { ItemsScreen(navController = navController, viewModel = itemsViewModel) }
                    composable("itemDetail") { ItemDetailScreen(viewModel = itemsViewModel) }

                composable("shop") { ShopScreen(navController = navController, viewModel = shopViewModel) }
                    composable("shopDetail") { ShopDetailScreen(viewModel = shopViewModel) }

                composable("rewards") { BattlePassScreen(navController = navController, viewModel = battlepassViewModel) }
                    composable("battlepass_trailer") { BattlePassTrailerScreen(navController = navController, viewModel = battlepassViewModel) }

                composable("map") {
                    MapScreen()
                }

                composable("about") {
                    AboutScreen()
                }
            }

    }
}
