// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

struct ShopResponse: FortniteApiResponse {
    let result: Bool?
    let fullShop: Bool?
    let lastUpdate: ShopLastUpdate?
    let currentRotation: [String: String]?
    let nextRotation: String?
    let carousel: String?
    let specialOfferVideo: String?
    let customBackground: String?
    let shop: [ShopItem]
}

struct ShopLastUpdate: Codable {
    let date: String?
    let uid: String?
}

struct ShopItem: Codable {
    let mainId: String?
    let displayName: String?
    let displayDescription: String?
    let displayType: String?
    let mainType: String?
    let offerId: String?
    let devName: String?
    let webURL: String?
    let offerDates: ShopOfferDates?
    let colors: ShopColors?
    let displayAssets: [ShopDisplayAsset]?
    let firstReleaseDate: String?
    let previousReleaseDate: String?
    let giftAllowed: Bool?
    let buyAllowed: Bool?
    let price: ShopPrice?
    let rarity: ShopRarity?
    let series: ShopSeries?
    let banner: ShopBanner?
    let offerTag: ShopOfferTag?
    let granted: [ShopGrantedItem]?
    let priority: Int?
    let section: ShopSection?
    let groupIndex: Int?
    let storeName: String?
    let tileSize: String?
    let categories: [String]?
}

struct ShopOfferDates: Codable {
    let in_date: String?
    let out_date: String?

    enum CodingKeys: String, CodingKey {
        case in_date = "in"
        case out_date = "out"
    }
}

struct ShopColors: Codable {
    let color1: String?
    let color2: String?
    let color3: String?
    let textBackgroundColor: String?
}

struct ShopDisplayAsset: Codable {
    let displayAsset: String?
    let materialInstance: String?
    let primaryMode: String?
    let productTag: String?
    let url: String?
    let flipbook: String?
    let background_texture: String?
    let background_url: String?
    let full_background: String?
    
    enum CodingKeys: String, CodingKey {
        case displayAsset
        case materialInstance
        case primaryMode
        case productTag
        case url
        case flipbook
        case background_texture
        case background_url = "background"
        case full_background
    }
}

struct ShopPrice: Codable {
    let regularPrice: Int?
    let finalPrice: Int?
    let floorPrice: Int?
}

struct ShopRarity: Codable {
    let id: String?
    let name: String?
}

struct ShopSeries: Codable {
    let id: String?
    let name: String?
}

struct ShopBanner: Codable {
    let id: String?
    let name: String?
    let intensity: String?
}

struct ShopOfferTag: Codable {
    let id: String?
    let text: String?
}

struct ShopGrantedItem: Codable, Identifiable {
    let id: String?
    let type: ShopItemType?
    let name: String?
    let description: String?
    let rarity: ShopRarity?
    let series: ShopSeries?
    let images: ShopImages?
    let juno: ShopIconHolder?
    let beans: ShopIconHolder?
    let video: String?
    let audio: String?
    let gameplayTags: [String]?
    let set: ShopItemSet?
}

struct ShopItemType: Codable {
    let id: String?
    let name: String?
}

struct ShopImages: Codable {
    let icon: String?
    let featured: String?
    let background: String?
    let iconBackground: String?
    let fullBackground: String?

    enum CodingKeys: String, CodingKey {
        case icon
        case featured
        case background
        case iconBackground = "icon_background"
        case fullBackground = "full_background"
    }
}

struct ShopIconHolder: Codable {
    let icon: String?
}

struct ShopItemSet: Codable {
    let id: String?
    let name: String?
    let partOf: String?
}

struct ShopSection: Codable {
    let id: String?
    let name: String?
    let category: String?
    let landingPriority: Int?
    let metadata: ShopSectionMetadata?
}

struct ShopSectionMetadata: Codable {
    let textureMetadata: [ShopTextureMetadata]?
}

struct ShopTextureMetadata: Codable {
    let key: String?
    let value: String?
}
