// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation


class FortniteApiClient
{
    static let instance = FortniteApiClient()
    
    private var apiKey = "Get API key at https://fortniteapi.io"
    
    private let baseUrl = "https://fortniteapi.io"
    
    private let session = URLSession.shared
    private let jsonDecoder: JSONDecoder
    
    init()
    {
        // Setup JSON decoder
        jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .useDefaultKeys
    }
    
    // Helper method to create URLRequest with common headers
    private func createRequest(for endpoint: String, queryItems: [URLQueryItem]) -> URLRequest
    {
        var components = URLComponents(string: baseUrl + endpoint)!
        components.queryItems = queryItems
        
        var request = URLRequest(url: components.url!)
        request.addValue(apiKey, forHTTPHeaderField: "Authorization")
        return request
    }
    
    // Generic fetch method
    private func fetch<T: Decodable>(request: URLRequest) async throws -> T {
        let (data, response) = try await session.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse,
              httpResponse.statusCode == 200 else {
            throw URLError(.badServerResponse)
        }
        
        return try jsonDecoder.decode(T.self, from: data)
    }
    
    // API Methods
    func getBattleRoyaleNews() async throws -> NewsResponse {
        let queryItems = [URLQueryItem(name: "lang", value: "en"),
                          URLQueryItem(name: "type", value: "br")]
        let request = createRequest(for: "/v1/news", queryItems: queryItems)
        return try await fetch(request: request)
    }
    
    func upcomingItems() async throws -> ItemsResponse {
        let queryItems = [URLQueryItem(name: "lang", value: "en")]
        let request = createRequest(for: "/v2/items/upcoming", queryItems: queryItems)
        return try await fetch(request: request)
    }
    
    func getDailyShop() async throws -> ShopResponse {
        let queryItems = [URLQueryItem(name: "lang", value: "en")]
        let request = createRequest(for: "/v2/shop", queryItems: queryItems)
        return try await fetch(request: request)
    }
    
    func getBattlePassRewards() async throws -> BattlePassResponse {
        let queryItems = [URLQueryItem(name: "lang", value: "en"),
                          URLQueryItem(name: "season", value: "current")]
        let request = createRequest(for: "/v2/battlepass", queryItems: queryItems)
        return try await fetch(request: request)
    }
    
    func getMapImageUrl(showPOI: Bool = false) -> URL?
    {
        return URL(string: "https://media.fortniteapi.io/images/map.png?showPOI=\(showPOI)")
    }
    
    func getServerStatus() async throws -> StatusResponse {
        let statusUrl = URL(string: "https://status.epicgames.com/api/v2/summary.json")!
        let request = URLRequest(url: statusUrl)
        return try await fetch(request: request)
    }
}

