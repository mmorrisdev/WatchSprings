// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

@Observable
class ShopViewModel
{
    var shopResponse: ShopResponse? = nil
    var selectedItem: ShopItem? = nil
    var isLoading: Bool = false

    func select(item: ShopItem)
    {
        selectedItem = item
    }

    func refreshShop()
    {
        guard shopResponse == nil else { return } // Already loaded
   
        isLoading = true

        Task {
            do {
                let result = try await FortniteApiClient.instance.getDailyShop()
                await MainActor.run {
                    self.shopResponse = result
                    self.isLoading = false
                    print("Shop items loaded: \(result.shop.count)")
                }
            } catch {
                await MainActor.run {
                    print("Error loading shop items: \(error.localizedDescription)")
                    self.isLoading = false
                }
            }
        }
    }
}
