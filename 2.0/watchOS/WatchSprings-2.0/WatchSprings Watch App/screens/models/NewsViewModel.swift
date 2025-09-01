// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

@Observable
class NewsViewModel
{
    var selectedNews: NewsItem? = nil
    var newsResponse: NewsResponse? = nil
    var isLoading: Bool = false
    
    func select(news: NewsItem)
    {
        selectedNews = news
    }
    
    func refreshNews()
    {
        guard newsResponse == nil else { return } // Already loaded
        
        isLoading = true
        
        Task {
            do {
                let result = try await FortniteApiClient.instance.getBattleRoyaleNews()
                await MainActor.run {
                    self.newsResponse = result
                    self.isLoading = false
                    print("News result: \(String(describing: result))")
                }
            } catch {
                await MainActor.run {
                    print("Error: \(error.localizedDescription)")
                    self.isLoading = false
                }
            }
        }
    }
}
