// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI
import Kingfisher

struct NewsDetailScreen: View
{
    @State var viewModel: NewsViewModel

    var body: some View
    {
        if let news = viewModel.selectedNews
        {
            ChildScreen(title: news.title ?? "News Detail", isLoading: false)
            {
                VStack(spacing: 16)
                {
                    if let url = URL(string: news.image ?? "News Detail")
                    {
                        KFImage(url)
                            .placeholder {
                                LoadingImage()
                            }
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                    }
                    
                    Text(news.body ?? "News Detail")
                        .font(.body)
                        .padding()
                }
            }
            .padding()
        }
    }
}
