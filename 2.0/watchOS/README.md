# WatchSprings — watchOS 10

 WatchSprings for Apple Watch was first introduced before Apple enabled stand alone watch apps to be published independently. For this reason WatchSprings 2.0 for Apple Watch is still an iOS watch companion application.

### Overview
- **Architecture:** MVVM with small, focused view models and pure SwiftUI views.
- **Navigation:** `NavigationStack` with a type-safe `Screen` enum for routes.
- **State:** `@Observable` view models mostly, with `@State` bindings for local view variables.
- **Concurrency:** `async/await`, `Task` called from .onAppear.
- **Networking:** `URLSession` + `Codable` wrapped in `FortniteApiClient` singleton
- **Images & Cache:** Kingfisher for remote images and caching. Images are slow to load on watch Wifi.

### Screens
- **Status** — `screens/StatusScreen.swift`, ViewModel: `screens/models/StatusViewModel.swift`
- **Battle Royale Updates (News)** — `screens/NewsScreen.swift`, ViewModel: `screens/models/NewsViewModel.swift`, Detail: `screens/NewsDetailScreen.swift`
- **Upcoming Items** — `screens/ItemsScreen.swift`, ViewModel: `screens/models/ItemsViewModel.swift`, Detail: `screens/ItemDetailScreen.swift`
- **Daily Shop** — `screens/ShopScreen.swift`, ViewModel: `screens/models/ShopViewModel.swift`, Detail: `screens/ShopDetailScreen.swift`
- **Battle Pass Rewards** — `screens/BattlePassScreen.swift`, ViewModel: `screens/models/BattlePassViewModel.swift`, Trailer: `screens/BattlePassTrailerScreen.swift`
- **Current Map** — `screens/MapScreen.swift`
- **About** — `screens/AboutScreen.swift`
- **Root Navigation** — `screens/MainScreen.swift`

### Architecture
- **Pattern:** MVVM. Views (aka Screens) use a ChildScreen "base class" via () -> Content
- **Composition:** Small `View` structs + dedicated `ViewModel` per screen.
- **Routing:** `enum Screen : Hashable` + `NavigationStack`/`NavigationLink` with typed data in the path.
- **State Management:** watchOS 10 `@Observable` with `@State`.
- **Domain Models:** `Codable` structs mirroring Fortnite API responses with safe optionals.
- **Abstractions:** `FortniteApiClient` isolates networking from UI; view models depend on it.

### Networking
- **Transport**: `URLSession` with `JSONDecoder` to FortniteApiResponse: Codable.
- **Auth header**: `Authorization: <API Key>` on fortniteapi.io calls (built in request helper).
- **Helpers**: `fetch<T: Decodable>(request:)` centralizes status/decoding.
- **Endpoints** (implemented via `FortniteAPI.swift`):
  - News → `/v2/news`
  - Daily Shop → `/v2/shop`
  - Battle Pass Rewards → `/v2/battlepass`
  - Map image URL (POI toggle) → `media.fortniteapi.io/images/map.png`
  - Service status → `status.epicgames.com/api/v2/summary.json`

### Dependencies
- **Kingfisher** — Swift package `https://github.com/onevcat/Kingfisher.git` (≥ 8.5.0).

### UI/UX
- **Layouts:** `ScrollView`/lazy containers; compact cards; `GeometryReader` where helpful.
- **Images:** Kingfisher for remote images/caching; placeholders for loading.
- **Interactions:** NavigationStack with dynamic .navigationDestination off of MainScreen.

### Building
- **Xcode 16** 
- **Deployment:** watchOS **10.6**; iOS stub **17.6**.
- **Open:** `WatchSprings.xcodeproj` project in Xcode.
- **Select scheme:** **WatchSprings Watch App** and run on a watchOS simulator/device.
- **API key:** required in `WatchSprings Watch App/api/FortniteAPI.swift`.
 
**Targets/Schemes**
- **WatchSprings** (iOS stub target; packaging only, no iPhone app installs).
- **WatchSprings Watch App** (independent watchOS app).

© 2025 Mark S. Morris. All rights reserved.

WatchSprings is Fan Content pursuant to the [Epic Games Fan Content Policy](https://www.epicgames.com/site/en-US/fan-art-policy). Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc. All rights reserved by Epic. This material is not official and is not endorsed by Epic. Epic, Epic Games, the Epic Games logo, Fortnite, the Fortnite logo, Unreal, Unreal Engine, the Unreal Engine logo, Unreal Tournament, and the Unreal Tournament logo are trademarks or registered trademarks of Epic Games, Inc. in the United States of America and elsewhere.