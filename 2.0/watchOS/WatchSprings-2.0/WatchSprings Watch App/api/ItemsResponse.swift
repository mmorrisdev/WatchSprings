// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

struct ItemsResponse: FortniteApiResponse, Codable {
    let result: Bool
    let lastUpdate: LastUpdate
    let items: [Item]
}

struct LastUpdate: Codable {
    let date: String
    let uid: String
}

struct Item: Codable {
    let id: String
    let type: ItemType
    let name: String
    let description: String
    let rarity: ItemRarity
    let series: ItemSeries?
    let price: Int
    let added: ItemAdded
    let builtInEmote: EmoteInfo?
    let copyrightedAudio: Bool
    let upcoming: Bool
    let reactive: Bool
    let releaseDate: String?
    let lastAppearance: String?
    let interest: Double
    let images: ItemImages
    let juno: ItemIconContainer
    let beans: ItemIconContainer
    let video: String?
    let audio: String?
    let path: String?
    let gameplayTags: [String]
    let apiTags: [String]
    let battlepass: String?
    let set: ItemSet?
}

struct ItemType: Codable {
    let id: String
    let name: String
}

struct ItemRarity: Codable {
    let id: String
    let name: String
}

struct ItemSeries: Codable {
    let id: String
    let name: String
}

struct ItemAdded: Codable {
    let date: String
    let version: String
}

struct EmoteInfo: Codable {
    let id: String
    let type: ItemType
    let name: String
    let description: String
    let rarity: ItemRarity
    let series: ItemSeries?
    let images: ItemImages
    let video: String?
}

struct ItemImages: Codable {
    let icon: String?
    let featured: String?
    let background: String?
    let icon_background: String?
    let full_background: String?
}

struct ItemIconContainer: Codable {
    let icon: String?
}

struct ItemSet: Codable {
    let id: String
    let name: String
    let partOf: String
}
