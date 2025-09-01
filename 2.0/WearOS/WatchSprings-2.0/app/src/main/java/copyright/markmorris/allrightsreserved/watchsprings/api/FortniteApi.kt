// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

//Get API key at https://fortniteapi.io/

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FortniteApiClient()
{
    private var apiKey =  "Get API key at https://fortniteapi.io/"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true
                        coerceInputValues = true })     // turns null into ""
        }
    }

    private val baseUrl = "https://fortniteapi.io"

    suspend fun getBattleRoyaleNews(): NewsResponse =
        client.get("$baseUrl/v1/news") {
            parameter("lang", "en")
            parameter("type", "br")
            header("Authorization", apiKey)
        }.body()

    suspend fun upcomingItems(): ItemsResponse =
        client.get("$baseUrl/v2/items/upcoming") {
            parameter("lang", "en")
            header("Authorization", apiKey)
        }.body()

    suspend fun getDailyShop(): ShopResponse =
        client.get("$baseUrl/v2/shop") {
            parameter("lang", "en")
            header("Authorization", apiKey)
        }.body()

    suspend fun getBattlePassRewards(): BattlePassResponse =
        client.get("$baseUrl/v2/battlepass") {
            parameter("lang", "en")
            parameter("season", "current")
            header("Authorization", apiKey)
        }.body()

    fun getMapImageUrl(showPOI: Boolean = false): String =
        "https://media.fortniteapi.io/images/map.png?showPOI=$showPOI"

    suspend fun getServerStatus(): StatusResponse =
        client.get("https://status.epicgames.com/api/v2/summary.json")
            .body()

}
