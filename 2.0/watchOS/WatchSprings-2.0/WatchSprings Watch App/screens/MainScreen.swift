// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI

enum Screen: Hashable
{
    case status
    case news
    case newsDetail
    case items
    case itemDetail
    case shop
    case shopDetail
    case rewards
    case map
    case about
}

let buttonLabels: [(String, Screen)] = [
    ("Battle Royale Updates", Screen.news),
    ("Upcoming Items", Screen.items),
    ("Daily Shop", Screen.shop),
    ("Battle Pass Rewards", Screen.rewards),
    ("Current Map", Screen.map),
    ("About", Screen.about)
]

struct MainScreen: View
{
    @State var viewModel: StatusViewModel
    
    var body: some View
    {
        ScrollView
        {
            VStack(spacing: 8)
            {
                Text("WatchSprings")
                    .font(.headline)
              
                TitleImage()
                
                Text("Fortnite Server Status:")
                    .font(.footnote)
                
                NavigationLink(value: Screen.status)
                {
                    ServerStatusPill(viewModel: viewModel)
                }
                .buttonStyle(.plain)
                
                ForEach(buttonLabels, id: \.1) { (title, screen) in
                    NavigationLink(title, value: screen)
                        .buttonStyle(.borderedProminent)
                }
            }
            .padding()
        }
        .onAppear
        {
            Task
            {
                await viewModel.refreshStatus()
            }
        }
    }
}

struct TitleImage: View
{
    var body: some View
    {
        Image("title")
            .resizable()
            .scaledToFit()
            .frame(maxWidth: .infinity)
    }
}

struct ServerStatusPill: View
{
    @State var viewModel: StatusViewModel
    
    var body: some View
    {
        ZStack
        {
            Text(label)
                .font(.caption2)
                .padding(.horizontal, 12)
                .padding(.vertical, 6)
                .frame(maxWidth: .infinity)
                .background(backgroundColor)
                .clipShape(Capsule())
                .foregroundColor(.white)
        }
    }
    
    private var label: String
    {
        if viewModel.isLoading {
            return "Getting status..."
        } else if viewModel.isFortniteOnline {
            return "Fortnite is up..."
        } else {
            return "Fortnite is down..."
        }
    }
    
    private var backgroundColor: Color
    {
        if viewModel.isLoading  {
            return .gray
        } else if viewModel.isFortniteOnline {
            return .green
        } else {
            return .red
        }
    }
}

