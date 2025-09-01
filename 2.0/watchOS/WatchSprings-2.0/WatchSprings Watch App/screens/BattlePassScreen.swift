// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI
import Kingfisher

struct BattlePassScreen: View
{
    @State var viewModel: BattlePassViewModel
    
    @State private var showTrailer: Bool = false

    var body: some View
    {
        ChildScreen(title: "Battle Pass", isLoading: viewModel.isLoading)
        {
            if let response = viewModel.battlePassResponse
            {
                VStack(spacing: 8)
                {
                    if let displayInfo = response.displayInfo
                    {
                        if let chapterSeason = displayInfo.chapterSeason
                        {
                            Text(chapterSeason)
                                .font(.caption)
                                .multilineTextAlignment(.center)
                        }

                        if let name = displayInfo.battlepassName
                        {
                            Text(name)
                                .font(.caption2)
                                .multilineTextAlignment(.center)
                        }
                    }

                    if let trailerUrl = response.videos?.first?.url
                    {
                        Button("View Trailer") {
                            viewModel.selectTrailer(url: trailerUrl)
                            showTrailer = true
                            // Trigger navigation in parent view
                        }
                        .buttonStyle(.borderedProminent)
                    }

                    ScrollView
                    {
                        LazyVStack(spacing: 12)
                        {
                            let enumRewards = Array((response.rewards ?? []).enumerated())

                            ForEach(enumRewards, id: \.offset) { index, reward in
                                if let item = reward.item {
                                    BattlePassRewardCard(item: item)
                                        .onTapGesture {
                                            viewModel.select(reward: reward)
                                            // Trigger navigation or detail action
                                        }
                                }
                            }
                        }
                        .padding(.top, 4)
                    }
                }
                .padding(.horizontal)
            }
        }
        .onAppear {
            viewModel.refreshBattlePass()
        }
        .navigationDestination(isPresented: $showTrailer) {
           BattlePassTrailerScreen(viewModel: viewModel)
        }
    }
}

struct BattlePassRewardCard: View
{
    let item: BattlePassItem

    var body: some View
    {
        VStack(spacing: 6)
        {
            BattlePassImageLayered(images: item.images)
                .frame(width: 100, height: 100)

            if let name = item.name
            {
                Text(name)
                    .font(.caption2)
                    .multilineTextAlignment(.center)
                    .lineLimit(2)
            }
        }
        .padding(6)
        .background(Color.gray.opacity(0.15))
        .cornerRadius(12)
    }
}

struct BattlePassImageLayered: View
{
    let images: BPItemImages?

    var body: some View
    {
        ZStack
        {
            if let bg = images?.icon_background, let bgUrl = URL(string: bg)
            {
                KFImage(bgUrl)
                    .placeholder {
                        LoadingImage()
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }

            if let icon = images?.icon, let iconUrl = URL(string: icon)
            {
                KFImage(iconUrl)
                    .placeholder {
                        Color.clear
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }
        }
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}
