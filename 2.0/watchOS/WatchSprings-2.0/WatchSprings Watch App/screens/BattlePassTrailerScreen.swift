// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.


import SwiftUI
import AVKit
import AVFoundation
import Combine

struct BattlePassTrailerScreen: View
{
    @State var viewModel: BattlePassViewModel
    
    @Environment(\.dismiss) private var dismiss
   
    @State private var player: AVPlayer? = nil
    @State private var playbackObserver: AnyCancellable?

    var body: some View
    {
        ChildScreen(title: "Trailer", isLoading: viewModel.isLoading)
        {
            VStack
            {
                if let urlString = viewModel.selectedTrailerUrl,
                   let videoURL = URL(string: urlString)
                {
                    VideoPlayer(player: player)
                        .onAppear {
                            let avPlayer = AVPlayer(url: videoURL)
                            player = avPlayer
                            avPlayer.play()
                            
                            playbackObserver = NotificationCenter.default
                                .publisher(for: .AVPlayerItemDidPlayToEndTime, object: avPlayer.currentItem)
                                .sink { _ in dismiss() }
                        }
                        .onDisappear {
                            playbackObserver?.cancel()
                            playbackObserver = nil
                        }
                        .frame(height: 120)
                    
                    Button("Close") {
                        dismiss()
                    }
                    .buttonStyle(.bordered)
                    
                } else {
                    Text("No trailer available")
                        .foregroundColor(.gray)
                }
            }
        }
    }
}
