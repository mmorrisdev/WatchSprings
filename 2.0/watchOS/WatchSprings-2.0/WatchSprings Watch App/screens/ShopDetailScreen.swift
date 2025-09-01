// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI
import Kingfisher

struct ShopDetailScreen: View
{
    @State var viewModel: ShopViewModel

    var body: some View
    {
        if let selectedItem = viewModel.selectedItem
        {
            ChildScreen(title: selectedItem.displayName ?? "Details", isLoading: false)
            {
                ScrollView {
                    LazyVStack(spacing: 12) {
                        
                        ShopImageLayered(item: selectedItem)
                            .padding(.bottom, 8)
                        
                        if let displayType = selectedItem.displayType {
                            Text(displayType)
                                .font(.caption)
                                .multilineTextAlignment(.center)
                                .frame(maxWidth: .infinity)
                        }
                        
                        if let price = selectedItem.price?.finalPrice {
                            Text("\(price) V-Bucks")
                                .font(.caption2)
                                .foregroundColor(.gray)
                                .multilineTextAlignment(.center)
                                .frame(maxWidth: .infinity)
                        }
                        
                        if let rarity = selectedItem.rarity?.name {
                            Text("Rarity: \(rarity)")
                                .font(.caption2)
                                .foregroundColor(.gray)
                                .multilineTextAlignment(.center)
                                .frame(maxWidth: .infinity)
                        }
                        
                        if let description = selectedItem.displayDescription {
                            Text(description)
                                .font(.body)
                                .multilineTextAlignment(.center)
                                .padding(8)
                                .frame(maxWidth: .infinity)
                        }
                        
                        if let grantedItems = selectedItem.granted, !grantedItems.isEmpty
                        {
                             Text("Includes:")
                             .font(.caption)
                             .multilineTextAlignment(.center)
                             .padding(.top, 8)
                             .frame(maxWidth: .infinity)
                             
                             ForEach(grantedItems) { grantedItem in
                                 GrantedItemCard(item: grantedItem)
                             }
                         }
                    }
                }
            }
        }
    }
}

struct GrantedItemCard: View
{
    let item: ShopGrantedItem
    
    var body: some View
    {
        VStack
        {
            if let iconUrl = item.images?.icon, let url = URL(string: iconUrl)
            {
                KFImage(url)
                    .placeholder {
                        LoadingImage()
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }
            else
            {
                Color.gray.opacity(0.3)
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .aspectRatio(1, contentMode: .fit)
            }
            
            Text(item.name ?? "Unnamed")
                .font(.caption2)
                .multilineTextAlignment(.center)
                .frame(maxWidth: .infinity)
        }
        .padding(8)
        .background(Color.gray.opacity(0.2))
        .clipShape(RoundedRectangle(cornerRadius: 8))
    }
}
