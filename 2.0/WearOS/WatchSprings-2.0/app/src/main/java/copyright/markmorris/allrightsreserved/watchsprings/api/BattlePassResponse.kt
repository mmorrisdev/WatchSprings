// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class BattlePassResponse(
    val result: Boolean? = null,
    val season: Int? = null,
    val displayInfo: DisplayInfo? = null,
    val seasonDates: SeasonDates? = null,
    val videos: List<BattlePassVideo>? = null,
    val rewards: List<BattlePassReward>? = null
)

@Serializable
data class DisplayInfo(
    val chapter: String? = null,
    val season: String? = null,
    val chapterSeason: String? = null,
    val battlepassName: String? = null
)

@Serializable
data class SeasonDates(
    val begin: String? = null,
    val end: String? = null
)

@Serializable
data class BattlePassVideo(
    val lang: String? = null,
    val type: String? = null,
    val url: String? = null
)

@Serializable
data class BattlePassReward(
    val offerId: String? = null,
    val tier: Int? = null,
    val page: Int? = null,
    val battlepass: String? = null,
    val quantity: Int? = null,
    val price: BattlePassPrice? = null,
    val rewardsNeededForUnlock: Int? = null,
    val levelsNeededForUnlock: Int? = null,
    val item: BattlePassItem? = null
)

@Serializable
data class BattlePassPrice(
    val type: String? = null,
    val amount: Int? = null
)

@Serializable
data class ItemSeries(
    val id: String? = null,
    val name: String? = null
)

@Serializable
data class BattlePassItem(
    val id: String? = null,
    val type: BPItemType? = null,
    val name: String? = null,
    val description: String? = null,
    val rarity: ItemRarity? = null,
    val series: ItemSeries? = null, // ← changed from String? to ItemSeries?
    val price: Int? = null,
    val added: ItemAdded? = null,
    val builtInEmote: String? = null,
    val copyrightedAudio: Boolean? = null,
    val upcoming: Boolean? = null,
    val reactive: Boolean? = null,
    val releaseDate: String? = null,
    val lastAppearance: String? = null,
    val interest: Double? = null,
    val images: ItemImages? = null,
    val juno: JunoOrBeans? = null,
    val beans: JunoOrBeans? = null,
    val video: String? = null,
    val audio: String? = null,
    val path: String? = null,
    val gameplayTags: List<JsonElement>? = null,
    val apiTags: List<JsonElement>? = null,
    val searchTags: List<JsonElement>? = null,
    val battlepass: BattlePassItemBattlepass? = null,
    val set: BPItemSet? = null,
    val introduction: ItemIntroduction? = null,
    val displayAssets: List<DisplayAsset>? = null,
    val shopHistory: List<JsonElement>? = null,
    val styles: List<BPStyle>? = null,
    val previewVideos: List<JsonElement>? = null,
    val epicVideos: List<JsonElement>? = null,
    val grants: List<JsonElement>? = null,
    val grantedBy: List<JsonElement>? = null
)


@Serializable
data class BPItemType(val id: String? = null, val name: String? = null)

@Serializable
data class ItemRarity(val id: String? = null, val name: String? = null)

@Serializable
data class ItemAdded(val date: String? = null, val version: String? = null)

@Serializable
data class ItemImages(
    val icon: String? = null,
    val featured: String? = null,
    val background: String? = null,
    val icon_background: String? = null,
    val full_background: String? = null
)

@Serializable
data class JunoOrBeans(val icon: String? = null)

@Serializable
data class BattlePassItemBattlepass(
    val season: Int? = null,
    val tier: Int? = null,
    val page: Int? = null,
    val type: String? = null,
    val displayText: DisplayInfo? = null,
    val battlePassName: String? = null
)

@Serializable
data class BPItemSet(
    val id: String? = null,
    val name: String? = null,
    val partOf: String? = null
)

@Serializable
data class ItemIntroduction(
    val chapter: String? = null,
    val season: String? = null,
    val text: String? = null
)

@Serializable
data class DisplayAsset(
    val background: String? = null,
    val url: String? = null
)

@Serializable
data class BPStyle(
    val name: String? = null,
    val channel: String? = null,
    val channelName: String? = null,
    val tag: String? = null,
    val isDefault: Boolean? = null,
    val startUnlocked: Boolean? = null,
    val hideIfNotOwned: Boolean? = null,
    val image: String? = null
)
