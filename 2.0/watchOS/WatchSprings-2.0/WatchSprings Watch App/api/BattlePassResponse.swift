// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

struct BattlePassResponse: Codable {
    let result: Bool?
    let season: Int?
    let displayInfo: DisplayInfo?
    let seasonDates: SeasonDates?
    let videos: [BattlePassVideo]?
    let rewards: [BattlePassReward]?
}

struct DisplayInfo: Codable {
    let chapter: String?
    let season: String?
    let chapterSeason: String?
    let battlepassName: String?
}

struct SeasonDates: Codable {
    let begin: String?
    let end: String?
}

struct BattlePassVideo: Codable {
    let lang: String?
    let type: String?
    let url: String?
}

struct BattlePassReward: Codable {
    let offerId: String?
    let tier: Int?
    let page: Int?
    let battlepass: String?
    let quantity: Int?
    let price: BattlePassPrice?
    let rewardsNeededForUnlock: Int?
    let levelsNeededForUnlock: Int?
    let item: BattlePassItem?
}

struct BattlePassPrice: Codable {
    let type: String?
    let amount: Int?
}

struct BattlePassItem: Codable {
    let id: String?
    let type: BPItemType?
    let name: String?
    let description: String?
    let rarity: BPItemRarity?
    let series: BPItemSeries?
    let price: Int?
    let added: BPItemAdded?
    let builtInEmote: String?
    let copyrightedAudio: Bool?
    let upcoming: Bool?
    let reactive: Bool?
    let releaseDate: String?
    let lastAppearance: String?
    let interest: Double?
    let images: BPItemImages?
    let juno: JunoOrBeans?
    let beans: JunoOrBeans?
    let video: String?
    let audio: String?
    let path: String?
    let gameplayTags: [JsonElement]?
    let apiTags: [JsonElement]?
    let searchTags: [JsonElement]?
    let battlepass: BattlePassItemBattlepass?
    let set: BPItemSet?
    let introduction: ItemIntroduction?
    let displayAssets: [DisplayAsset]?
    let shopHistory: [JsonElement]?
    let styles: [BPStyle]?
    let previewVideos: [JsonElement]?
    let epicVideos: [JsonElement]?
    let grants: [JsonElement]?
    let grantedBy: [JsonElement]?
}

struct BPItemType: Codable {
    let id: String?
    let name: String?
}

struct BPItemRarity: Codable {
    let id: String?
    let name: String?
}

struct BPItemSeries: Codable {
    let id: String?
    let name: String?
}

struct BPItemAdded: Codable {
    let date: String?
    let version: String?
}

struct BPItemImages: Codable {
    let icon: String?
    let featured: String?
    let background: String?
    let icon_background: String?
    let full_background: String?
}

struct JunoOrBeans: Codable {
    let icon: String?
}

struct BattlePassItemBattlepass: Codable {
    let season: Int?
    let tier: Int?
    let page: Int?
    let type: String?
    let displayText: DisplayInfo?
    let battlePassName: String?
}

struct BPItemSet: Codable {
    let id: String?
    let name: String?
    let partOf: String?
}

struct ItemIntroduction: Codable {
    let chapter: String?
    let season: String?
    let text: String?
}

struct DisplayAsset: Codable {
    let background: String?
    let url: String?
}

struct BPStyle: Codable {
    let name: String?
    let channel: String?
    let channelName: String?
    let tag: String?
    let isDefault: Bool?
    let startUnlocked: Bool?
    let hideIfNotOwned: Bool?
    let image: String?
}
