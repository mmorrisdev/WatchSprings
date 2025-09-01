// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.


import SwiftUI
import Kingfisher
import WatchKit

struct MapScreen: View
{
    @State var viewModel: MapViewModel
    
    @State private var isLoading = true
    
    var body: some View
    {
        ChildScreen(title: "👉 Current Map 👆", isLoading: isLoading)
        {
            GeometryReader { geometry in
                ScrollableMapViewer(
                    viewModel: viewModel,
                    onLoadFinished: {
                        isLoading = false
                    }
                )
            }
        }
    }
}

struct ScrollableMapViewer: View
{
    @State var viewModel: MapViewModel
    
    var onLoadFinished: () -> Void
    
    var body: some View
    {
        ZStack
        {
            if let map = FortniteApiClient.instance.getMapImageUrl(showPOI: true)
            {
                KFImage(map)
                    .resizable()
                    .retry(maxCount: 3, interval: .seconds(5))
                    .onSuccess { result in
                        onLoadFinished()
                    }
                    .frame(width: viewModel.mapSizeW, height: viewModel.mapSizeH)
                    .offset(viewModel.offset)
                    .gesture(
                        DragGesture().onChanged { value in
                            viewModel.updateOffset(value)
                        }
                    )
            }
            
        }
        .clipped()
    }
}

