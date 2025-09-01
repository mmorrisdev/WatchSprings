// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI

struct AboutScreen: View
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
        ChildScreen(title: "About")
        {
           
            TitleImage()

            Text(aboutText)
                .font(.footnote)
                .multilineTextAlignment(.center)
                .padding(.horizontal)

            Image("titlesquare") // Replace with your asset name
                .resizable()
                .frame(width: 48, height: 48)
        }
    }
}
