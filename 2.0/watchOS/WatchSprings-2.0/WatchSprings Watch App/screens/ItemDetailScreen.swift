// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI

struct ItemDetailScreen: View
{
    @State var viewModel: ItemsViewModel

    var body: some View
    {
        if let item = viewModel.selectedItem
        {
            ChildScreen(title: item.name, isLoading: false)
            {
                VStack(spacing: 12)
                {
                    ItemImageLayered(images: item.images)
                        .frame(width: 100, height: 100)
                    
                    if !item.description.isEmpty {
                        Text(item.description)
                            .font(.body)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal)
                    }
                    
                    if !item.rarity.name.isEmpty {
                        Text("Rarity: \(item.rarity.name)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                    
                    if let series = item.series?.name, !series.isEmpty {
                        Text("Series: \(series)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                    
                    if let setName = item.set?.name, !setName.isEmpty {
                        Text("Set: \(setName)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                    
                }
                .padding(8)
            }
        }
    }
}

