// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI
import Kingfisher

struct ShopScreen: View
{
    @State var viewModel: ShopViewModel

    var body: some View
    {
        ChildScreen(title: "Daily Shop" , isLoading: viewModel.isLoading)
        {
            LazyVStack(spacing: 8)
            {
                ForEach(viewModel.shopResponse?.shop ?? [], id: \.mainId) { item in
                    NavigationLink(value: Screen.shopDetail) {
                        ShopCard(item: item)
                    }
                    .simultaneousGesture(TapGesture().onEnded {
                        viewModel.selectedItem = item
                    })
                    .buttonStyle(PlainButtonStyle())
                }
            }
        }
        .onAppear
        {
            Task
            {
                viewModel.refreshShop()
            }
        }
    }
}

struct ShopCard: View
{
    let item: ShopItem

    var body: some View
    {
        VStack(spacing: 4)
        {
            ShopImageLayered(item: item)

            Text(item.displayName ?? "Shop Item")
                .font(.caption2)
                .multilineTextAlignment(.center)
        }
        .padding(6)
        .background(Color.gray.opacity(0.2))
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}

struct ShopImageLayered: View
{
    let item: ShopItem

    var body: some View
    {
        ZStack
        {
            if let bgURL = URL(string: item.displayAssets?.first?.background_url ?? "")
            {
                
                KFImage(bgURL)
                    .placeholder {
                        LoadingImage()
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }

            if let iconURL = URL(string: item.displayAssets?.first?.url ?? "")
            {
                KFImage(iconURL)
                    .placeholder {
                        Color.clear
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }
        }
        .clipShape(RoundedRectangle(cornerRadius: 8))
    }
}
