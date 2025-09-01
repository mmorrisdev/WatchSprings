// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

@Observable
class ItemsViewModel
{
    var itemsResponse: ItemsResponse? = nil
    var selectedItem: Item? = nil
    var isLoading: Bool = false

    func select(item: Item) {
        selectedItem = item
    }

    func refreshItems() {
        guard itemsResponse == nil else { return } // Already loaded
   
        isLoading = true

        Task {
            do {
                let result = try await FortniteApiClient.instance.upcomingItems()
                await MainActor.run {
                    self.itemsResponse = result
                    self.isLoading = false
                    print("Items loaded: \(result.items.count)")
                }
            } catch {
                await MainActor.run {
                    print("Error loading items: \(error.localizedDescription)")
                    self.isLoading = false
                }
            }
        }
    }
}
