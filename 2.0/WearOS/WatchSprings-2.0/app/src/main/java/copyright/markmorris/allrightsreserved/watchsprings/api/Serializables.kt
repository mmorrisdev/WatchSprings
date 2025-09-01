// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface FortniteApiResponse : java.io.Serializable

@Serializable
data class NewsResponse (
    val result: Boolean,
    val type: String,
    val lang: String,
    val show: Long,
    val news: List<News>
) : FortniteApiResponse

@Serializable
data class News (
    val id: String,
    val title: String,
    val tabTitle: String,
    val date: String,
    val body: String,
    val adspace: String,
    val image: String,
    val live: Boolean,
    val video: String? = null
)

// ------------------------------------------------------------------------------------------------------------------------

@kotlinx.serialization.Serializable
data class ItemsResponse(
    val result: Boolean,
    val lastUpdate: LastUpdate,
    val items: List<FortniteItem>
) : FortniteApiResponse

@kotlinx.serialization.Serializable
data class LastUpdate(
    val date: String,
    val uid: String
)

@kotlinx.serialization.Serializable
data class FortniteItem(
    val id: String,
    val type: ItemType,
    val name: String,
    val description: String,
    val rarity: Rarity,
    val series: Series? = null,
    val price: Int,
    val images: ImageSet,
    val set: ItemSet? = null,
    val gameplayTags: List<String> = emptyList()
)

@kotlinx.serialization.Serializable
data class ItemType(val id: String, val name: String)

@kotlinx.serialization.Serializable
data class Rarity(val id: String, val name: String)

@kotlinx.serialization.Serializable
data class Series(val id: String, val name: String)

@kotlinx.serialization.Serializable
data class ImageSet(
    val icon: String? = null,
    val featured: String? = null,
    val background: String? = null,
    val icon_background: String? = null,
    val full_background: String? = null
)

@kotlinx.serialization.Serializable
data class ItemSet(
    val id: String,
    val name: String,
    val partOf: String
)

// ----------------------------------------------------------------------------------------------------------------------


@Serializable
data class StatusResponse(
    val page: StatusPage,
    val components: List<StatusComponent>,
    val incidents: List<StatusIncident>,
    val status: StatusSummary
) : FortniteApiResponse

@Serializable
data class StatusPage(
    val id: String,
    val name: String,
    val url: String,
    @SerialName("time_zone") val timeZone: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class StatusComponent(
    val id: String,
    val name: String,
    val status: String,
    @SerialName("group_id") val groupId: String? = null,
    @SerialName("page_id") val pageId: String,
    val group: Boolean,
    val components: List<String>? = null // only for group components
)

@Serializable
data class StatusSummary(
    val indicator: String,
    val description: String
)

@Serializable
data class StatusIncident(
    val id: String,
    val name: String,
    val status: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val impact: String? = null,
    val components: List<IncidentComponent> = emptyList(),
    @SerialName("incident_updates") val incidentUpdates: List<IncidentUpdate> = emptyList()
)

@Serializable
data class IncidentComponent(
    val id: String,
    val name: String,
    val status: String
)

@Serializable
data class IncidentUpdate(
    val id: String,
    val status: String,
    val body: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("affected_components") val affectedComponents: List<AffectedComponent> = emptyList()
)

@Serializable
data class AffectedComponent(
    val code: String,
    val name: String,
    @SerialName("old_status") val oldStatus: String,
    @SerialName("new_status") val newStatus: String
)

// ----------------------------------------------------------------------------------------------------------------------


