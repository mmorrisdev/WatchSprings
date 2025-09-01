// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI

@main
struct WatchApp: App
{
    @State var statusViewModel = StatusViewModel()
    @State var newsViewModel = NewsViewModel()
    @State var itemsViewModel = ItemsViewModel()
    @State var shopViewModel = ShopViewModel()
    @State var battlepassViewModel = BattlePassViewModel()
    @State var mapViewModel = MapViewModel()
      
    var body: some Scene
    {
        WindowGroup
        {
            NavigationStack
            {
                MainScreen(viewModel: statusViewModel)
                    .navigationDestination(for: Screen.self) { next in
                        mscreen(for: next)
                }
            }
        }
    }
    
    @ViewBuilder
    func mscreen(for hscreen: Screen) -> some View
    {
        switch hscreen
        {
            case .status:
                StatusScreen(viewModel: statusViewModel)
            
            case .news:
                NewsScreen(viewModel: newsViewModel)
            case .newsDetail:
                NewsDetailScreen(viewModel: newsViewModel)
            
            case .items:
                ItemsScreen(viewModel: itemsViewModel)
            case .itemDetail:
                ItemDetailScreen(viewModel: itemsViewModel)
            
            case .shop:
                ShopScreen(viewModel: shopViewModel)
            case .shopDetail:
                ShopDetailScreen(viewModel: shopViewModel)
            
            case .rewards:
                BattlePassScreen(viewModel: battlepassViewModel)
            
            case .map:
                MapScreen(viewModel: mapViewModel)
            
            case .about:
                AboutScreen()
            
            @unknown default:
                EmptyView()
        }
    }
}
