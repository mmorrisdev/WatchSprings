// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

@kotlinx.serialization.Serializable
data class ShopResponse(
    val result: Boolean? = null,
    val fullShop: Boolean? = null,
    val lastUpdate: ShopLastUpdate? = null,
    val currentRotation: Map<String, String>? = null,
    val nextRotation: String? = null,
    val carousel: String? = null,
    val specialOfferVideo: String? = null,
    val customBackground: String? = null,
    val shop: List<ShopItem>? = null
) : FortniteApiResponse

@kotlinx.serialization.Serializable
data class ShopLastUpdate(
    val date: String? = null,
    val uid: String? = null
)

@kotlinx.serialization.Serializable
data class ShopItem(
    val mainId: String? = null,
    val displayName: String? = null,
    val displayDescription: String? = null,
    val displayType: String? = null,
    val mainType: String? = null,
    val offerId: String? = null,
    val devName: String? = null,
    val webURL: String? = null,
    val offerDates: ShopOfferDates? = null,
    val colors: ShopColors? = null,
    val displayAssets: List<ShopDisplayAsset>? = null,
    val firstReleaseDate: String? = null,
    val previousReleaseDate: String? = null,
    val giftAllowed: Boolean? = null,
    val buyAllowed: Boolean? = null,
    val price: ShopPrice? = null,
    val rarity: ShopRarity? = null,
    val series: ShopSeries? = null,
    val banner: ShopBanner? = null,
    val offerTag: ShopOfferTag? = null,
    val granted: List<ShopGrantedItem>? = null,
    val priority: Int? = null,
    val section: ShopSection? = null,
    val groupIndex: Int? = null,
    val storeName: String? = null,
    val tileSize: String? = null,
    val categories: List<String>? = null
)

@kotlinx.serialization.Serializable
data class ShopOfferDates(
    @kotlinx.serialization.SerialName("in") val `in`: String? = null,
    val out: String? = null
)

@kotlinx.serialization.Serializable
data class ShopColors(
    val color1: String? = null,
    val color2: String? = null,
    val color3: String? = null,
    val textBackgroundColor: String? = null
)

@kotlinx.serialization.Serializable
data class ShopDisplayAsset(
    val displayAsset: String? = null,
    val materialInstance: String? = null,
    val primaryMode: String? = null,
    val productTag: String? = null,
    val url: String? = null,
    val flipbook: String? = null,
    val background_texture: String? = null,
    val background: String? = null,
    val full_background: String? = null
)

@kotlinx.serialization.Serializable
data class ShopPrice(
    val regularPrice: Int? = null,
    val finalPrice: Int? = null,
    val floorPrice: Int? = null
)

@kotlinx.serialization.Serializable
data class ShopRarity(
    val id: String? = null,
    val name: String? = null
)

@kotlinx.serialization.Serializable
data class ShopSeries(
    val id: String? = null,
    val name: String? = null
)

@kotlinx.serialization.Serializable
data class ShopBanner(
    val id: String? = null,
    val name: String? = null,
    val intensity: String? = null
)

@kotlinx.serialization.Serializable
data class ShopOfferTag(
    val id: String? = null,
    val text: String? = null
)

@kotlinx.serialization.Serializable
data class ShopGrantedItem(
    val id: String? = null,
    val type: ShopItemType? = null,
    val name: String? = null,
    val description: String? = null,
    val rarity: ShopRarity? = null,
    val series: ShopSeries? = null,
    val images: ShopImages? = null,
    val juno: ShopIconHolder? = null,
    val beans: ShopIconHolder? = null,
    val video: String? = null,
    val audio: String? = null,
    val gameplayTags: List<String>? = null,
    val set: ShopItemSet? = null
)

@kotlinx.serialization.Serializable
data class ShopItemType(
    val id: String? = null,
    val name: String? = null
)

@kotlinx.serialization.Serializable
data class ShopImages(
    val icon: String? = null,
    val featured: String? = null,
    val background: String? = null,
    @kotlinx.serialization.SerialName("icon_background") val iconBackground: String? = null,
    @kotlinx.serialization.SerialName("full_background") val fullBackground: String? = null
)

@kotlinx.serialization.Serializable
data class ShopIconHolder(
    val icon: String? = null
)

@kotlinx.serialization.Serializable
data class ShopItemSet(
    val id: String? = null,
    val name: String? = null,
    val partOf: String? = null
)

@kotlinx.serialization.Serializable
data class ShopSection(
    val id: String? = null,
    val name: String? = null,
    val category: String? = null,
    val landingPriority: Int? = null,
    val metadata: ShopSectionMetadata? = null
)

@kotlinx.serialization.Serializable
data class ShopSectionMetadata(
    val textureMetadata: List<ShopTextureMetadata>? = null
)

@kotlinx.serialization.Serializable
data class ShopTextureMetadata(
    val key: String? = null,
    val value: String? = null
)
