// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.


import SwiftUI
import WatchKit

struct ChildScreen<Content: View>: View
{
    let title: String
    let isLoading: Bool
    let content: () -> Content

    init(title: String, isLoading: Bool = false, @ViewBuilder content: @escaping () -> Content)
    {
        self.title = title
        self.isLoading = isLoading
        self.content = content
    }

    var body: some View
    {
        ScrollView
        {
            LazyVStack()
            {
                Text(title)
                    .font(.caption2)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal)
                    .lineLimit(2)

                if isLoading {
                    LoadingImage()
                }

                content()
            }
        }
        .background(Color.black)
    }
}

struct LoadingImage: View
{
    var body: some View
    {
        ZStack
        {
            Image("loading")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: .infinity)
                .background(Color.black)
                .opacity(0.2)
            
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle())
                .scaleEffect(1.2) // Adjust size for visibility
                .tint(.white) // Ensure visibility against black background
        }
    }
}
