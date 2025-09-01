// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation
import Kingfisher

func print(_ items: Any...)
{
    #if DEBUG
    Swift.print("DEBUG " + Date().description,items[0])
    #endif
}

@Observable
class StatusViewModel
{
    var fortniteComponents: [StatusComponent] = []
    var isFortniteOnline: Bool = false
    var isLoading: Bool = false
    var error: String?
    
    init()
    {
        ImageCache.default.clearCache()
    }

    func refreshStatus() async
    {
        guard fortniteComponents.isEmpty else { return }

        isLoading = true
        defer { isLoading = false }

        do {
            let result = try await FortniteApiClient.instance.getServerStatus()
            let components = result.components

            // Locate the Fortnite group component
            if let fortniteGroup = components.first(where: { $0.name == "Fortnite" && $0.group }) {
                isFortniteOnline = fortniteGroup.status == "operational"
                fortniteComponents = components.filter { $0.groupId == fortniteGroup.id }
            } else {
                isFortniteOnline = false
                fortniteComponents = []
            }
        } catch {
            self.error = "Failed to load status: \(error.localizedDescription)"
            print("Error fetching status: \(error)")
        }
    }
}

