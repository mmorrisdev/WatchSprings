// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI
import Kingfisher

struct ItemsScreen: View
{
    @State var viewModel: ItemsViewModel

    var body: some View
    {
        ChildScreen(title: "Upcoming\nItems" , isLoading: viewModel.isLoading)
        {
            LazyVStack(spacing: 8)
            {
                ForEach(viewModel.itemsResponse?.items ?? [], id: \.id) { item in
                    NavigationLink(value: Screen.itemDetail) {
                        ItemCard(item: item)
                    }
                    .simultaneousGesture(TapGesture().onEnded {
                        viewModel.selectedItem = item
                    })
                    .buttonStyle(.plain)
                }
            }
        }
        .onAppear
        {
            Task
            {
                viewModel.refreshItems()
            }
        }
    }
}

struct ItemCard: View
{
    let item: Item

    var body: some View
    {
        VStack(spacing: 4)
        {
            ItemImageLayered(images: item.images)

            Text(item.name)
                .font(.caption2)
                .multilineTextAlignment(.center)
        }
        .padding(6)
        .background(Color.gray.opacity(0.2))
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}

struct ItemImageLayered: View
{
    let images: ItemImages

    var body: some View
    {
        ZStack
        {
            if let bgURL = URL(string: images.icon_background ?? "")
            {
                KFImage(bgURL)
                    .placeholder {
                        LoadingImage()
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }

            if let iconURL = URL(string: images.icon ?? "")
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
