// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI
import Kingfisher

struct NewsScreen: View
{
    @State var viewModel: NewsViewModel

    var body: some View
    {
        ChildScreen(title: "Battle Royale", isLoading: viewModel.isLoading)
        {
            LazyVStack(spacing: 8)
            {
                ForEach(viewModel.newsResponse?.news ?? [], id: \.id) { news in
                    NavigationLink(value: Screen.newsDetail) {
                        NewsCard(news: news)
                    }
                    .simultaneousGesture(TapGesture().onEnded {
                        viewModel.selectedNews = news
                    })
                    .buttonStyle(.plain)
                }
            }
        }
        .onAppear
        {
            Task
            {
                viewModel.refreshNews()
            }
        }
        
    }
}

struct NewsCard: View
{
    let news: NewsItem

    var body: some View
    {
        VStack(spacing: 4)
        {
            if let imageURL = URL(string: news.image ?? "")
            {
                KFImage(imageURL)
                    .placeholder {
                        LoadingImage()
                            .aspectRatio(contentMode: .fit)
                    }
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            }

            Text(news.title ?? "Untitled")
                .font(.caption2)
                .multilineTextAlignment(.center)
        }
        .padding(6)
        .background(Color.gray.opacity(0.2))
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}

