# WatchSprings 2.0

**WatchSprings 2.0** is a complete rewrite in declarative UI and a new  API provider, implemented natively for both **watchOS** (SwiftUI) and **Wear OS** (Compose on Wear OS). The projects share a common architecture and naming conventions.

The FortniteAPI class now uses API provider [FortniteAPI.io](https://fortniteapi.io/) - for data retrieval an API key is required. WatchSprings runs with limited functionality without API key. WatchSprings shared features on both platforms are -


- **Architecture:** MVVM on both platforms.
  - `ViewModel` classes encapsulate API + state logic.
  - `@Observable` (Swift) / `StateFlow` (Kotlin) expose state to the UI.
- **Navigation:**
  - Swift: `NavigationStack` with a `Screen` enum.
  - Kotlin: `SwipeDismissableNavHost` with typed route strings.
- **Screens:** Identical feature set on both:
  - MainScreen
  - Status
  - Battle Royale News + Detail
  - Upcoming Items + Detail
  - Daily Shop + Detail
  - Battle Pass Rewards + Trailer
  - Current Map
  - About
- **File Structure:** Mirrors across projects:
  - `screens/` → JetPack Composables / SwiftUI Views.
  - `screens/models/` → ViewModels.
  - `api/` → Fortnite API client and de-serialization support.
- **Naming:** Same file and class names where possible (`NewsScreen`, `ShopViewModel`, etc.).

- **Child Screen Wrapper:**
  - Swift: `ChildScreen` view struct with content: () -> Content for derived screens
  - Kotlin: `ChildScreen` @Composable with content: @Composable () -> Unit for derived screens
- **Networking:**
  - Swift: `URLSession` + `Codable`.
  - Kotlin: `Ktor` + `kotlinx.serialization`.
  - Both centralize requests in `FortniteApiClient` equivalents.
- **Images & Caching:**
  - Swift: Kingfisher
  - Kotlin: Coil‑Compose
- **Video Playback on watch:**
  - Swift: SwiftUI AVPlayer
  - Kotlin: Compose AndroidView host for PlayerView

© 2025 Mark S. Morris. All rights reserved.

WatchSprings is Fan Content pursuant to the [Epic Games Fan Content Policy](https://www.epicgames.com/site/en-US/fan-art-policy). Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc. All rights reserved by Epic. This material is not official and is not endorsed by Epic. Epic, Epic Games, the Epic Games logo, Fortnite, the Fortnite logo, Unreal, Unreal Engine, the Unreal Engine logo, Unreal Tournament, and the Unreal Tournament logo are trademarks or registered trademarks of Epic Games, Inc. in the United States of America and elsewhere.