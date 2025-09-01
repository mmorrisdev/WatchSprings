// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI

struct ContentView: View
{
    var versionName: String
    {
        Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String ?? "Unknown"
    }

    var versionCode: String
    {
        Bundle.main.infoDictionary?["CFBundleVersion"] as? String ?? "0"
    }

    var aboutText: String
    {
        """
        WatchSprings
        © 2025
        Mark S. Morris
        All rights reserved

        Version \(versionName) (\(versionCode))

        WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc. All rights reserved by Epic. This material is not official and is not endorsed by Epic. Epic, Epic Games, the Epic Games logo, Fortnite, the Fortnite logo, Unreal, Unreal Engine, the Unreal Engine logo, Unreal Tournament, and the Unreal Tournament logo are trademarks or registered trademarks of Epic Games, Inc. in the United States of America and elsewhere.
        """
    }
    
    var body: some View
    {
        ZStack
        {
             Image("LaunchImage")
                .resizable()
                .scaledToFill()
                .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
                .ignoresSafeArea(.all)
           
            VStack
            {
                Spacer().frame(height: 40)
                
                VStack(spacing: 8)
                {
                    Text("Please install WatchSprings on your Apple Watch")
                        .font(.headline)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal)
                        .foregroundColor(.white)
                    
                    Button("Open Apple Watch App")
                    {
                        if let url = URL(string: "itms-watchs://")
                        {
                            UIApplication.shared.open(url)
                        }
                    }
                    .padding(.horizontal)
                    .background(Color.gray)
                    .foregroundStyle(.white)
                    .clipShape(Capsule())
                    
                    Text("Scroll down to Available Apps")
                        .font(.headline)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal)
                        .foregroundColor(.white)
                }
                
                Spacer()
                
                Text(aboutText)
                    .font(.footnote).bold()
                    .multilineTextAlignment(.center)
                    .padding(.horizontal)
                    .foregroundColor(.white)
                
                Spacer().frame(height: 40)
            }
        }
        .padding(.horizontal)
    }
    
}
