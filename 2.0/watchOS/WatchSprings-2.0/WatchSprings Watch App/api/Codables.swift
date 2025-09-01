// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

protocol FortniteApiResponse: Codable {}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------

struct NewsResponse: FortniteApiResponse {
    let result: Bool?
    let type: String?
    let lang: String?
    let show: Int?
    let news: [NewsItem]?
}

struct NewsItem: Codable, Equatable {
    let id: String?
    let title: String?
    let tabTitle: String?
    let date: String?
    let body: String?
    let adspace: String?
    let image: String?
    let live: Bool?
    let video: String?
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------

struct StatusResponse: FortniteApiResponse {
    let components: [StatusComponent]
}

struct StatusComponent: Codable, Identifiable {
    let id: String
    let name: String
    let status: String
    let group: Bool
    let groupId: String?

    enum CodingKeys: String, CodingKey {
        case id, name, status, group
        case groupId = "group_id"
    }
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------

struct JsonElement: Codable {
    let value: Any

    init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()

        if let obj = try? container.decode([String: JsonElement].self) {
            value = obj
        } else if let arr = try? container.decode([JsonElement].self) {
            value = arr
        } else if let str = try? container.decode(String.self) {
            value = str
        } else if let num = try? container.decode(Double.self) {
            value = num
        } else if let bool = try? container.decode(Bool.self) {
            value = bool
        } else if container.decodeNil() {
            value = ()
        } else {
            throw DecodingError.dataCorruptedError(
                in: container,
                debugDescription: "Unsupported JSON value"
            )
        }
    }

    func encode(to encoder: Encoder) throws {
        var container = encoder.singleValueContainer()

        switch value {
        case let obj as [String: JsonElement]:
            try container.encode(obj)
        case let arr as [JsonElement]:
            try container.encode(arr)
        case let str as String:
            try container.encode(str)
        case let num as Double:
            try container.encode(num)
        case let bool as Bool:
            try container.encode(bool)
        case is Void:
            try container.encodeNil()
        default:
            let context = EncodingError.Context(
                codingPath: container.codingPath,
                debugDescription: "Invalid JSON value type"
            )
            throw EncodingError.invalidValue(value, context)
        }
    }
}
