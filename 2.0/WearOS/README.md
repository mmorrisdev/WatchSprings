# WatchSprings — Wear OS 5

 WatchSprings for Wear OS is built in Kotlin with Wear Compose, using the androidx-wear-compose libraries. Many paramaters are still provided by Android Compose libs, but main outlines such as ScalingLazyColumn use the Wear versions. The Android project uses version catalogs with .kts Kotlin-based Gradle scripts.

### Overview
- **Architecture:** MVVM with dedicated ViewModels under `screens/models`.
- **Navigation:** `SwipeDismissableNavHost` in `MainActivity.kt` → `screens/MainScreen.kt` (typed route constants in-file).
- **State:** `StateFlow` collected with `collectAsState()`.
- **Concurrency:** Kotlin coroutines + `viewModelScope`.
- **Networking:** **Ktor** (`client-core`, `cio`, `content-negotiation`, `kotlinx-json`) via `api/FortniteApi.kt` (class `FortniteApiClient`).
- **Images & Cache:** Coil-Compose (`AsyncImage`) for remote images and caching. Images are slow to load on watch Wifi.

### Screens
- **Status** — `screens/StatusScreen.kt`, ViewModel: `screens/models/StatusViewModel.kt`
- **Battle Royale Updates (News)** — `screens/NewsScreen.kt`, ViewModel: `screens/models/NewsViewModel.kt`, detail: `screens/NewsDetailScreen.kt`
- **Upcoming Items** — `screens/ItemsScreen.kt`, ViewModel: `screens/models/ItemsViewModel.kt`, detail: `screens/ItemDetailScreen.kt`
- **Daily Shop** — `screens/ShopScreen.kt`, ViewModel: `screens/models/ShopViewModel.kt`, detail: `screens/ShopDetailScreen.kt`
- **Battle Pass Rewards** — `screens/BattlePassScreen.kt`, ViewModel: `screens/models/BattlePassViewModel.kt`, trailer: `screens/BattlePassTrailerScreen.kt`
- **Current Map** — `screens/MapScreen.kt`
- **About** — `screens/AboutScreen.kt`
- **Root Navigation** — `screens/MainScreen.kt`; activity entry: `MainActivity.kt`

### Architecture
- **Pattern:** MVVM. Views (aka Screens) use a ChildScreen "base class" via @Composable (ScalingLazyListState) -> Unit
- **Composition:** `@Composable` functions render state from ViewModels in `screens/models`.
- **Routing:** `SwipeDismissableNavHost` (wear-navigation) with routes wired in `screens/MainScreen.kt` and launched from `MainActivity.kt`.
- **State Management:** ViewModels expose `StateFlow`; UI collects via `collectAsState()`.
- **Domain models:** Kotlin data classes under `api/` (nullable fields to handle schema drift).
- **Abstractions:** `FortniteApiClient` centralizes HTTP calls and headers.

### Networking
- **Transport:** Ktor + CIO engine + ContentNegotiation with `kotlinx.serialization` to interface FortniteApiResponse : java.io.Serializable
- **Base:** `https://fortniteapi.io` and Epic status API.
- **Auth header**: `Authorization: <API Key>` on fortniteapi.io calls (built in request helper).
- **Endpoints** (implemented via `FortniteAPI.kt`):
  - News → `/v2/news`
  - Daily Shop → `/v2/shop`
  - Battle Pass Rewards → `/v2/battlepass`
  - Map image URL (POI toggle) → `media.fortniteapi.io/images/map.png`
  - Service status → `status.epicgames.com/api/v2/summary.json`

### Dependencies
- **Coil Compose:** https://coil-kt.github.io/coil/compose { module = "io.coil-kt:coil-compose", version.ref = "coil" }

### UI/UX
- **Layouts:** Wear `Scaffold` with `ScalingLazyColumn` and cards for round and square devices.
- **Images:** Coil with placeholders and crossfade; downsampling sized to round displays.
- **Interactions:** `SwipeDismissableNavHost` with Wear composable routes off of MainActivity.kt

### Building
- **Android Studio Narwhal**
- **Open:** open folder `2.0/WearOS/WatchSprings-2.0` in Android Studio.
- **Run:** Select the **app** module and a Wear OS 5 emulator/device.
- **API key:** required in `watchsprings/api/FortniteApi.kt`.

© 2025 Mark S. Morris. All rights reserved.

WatchSprings is Fan Content pursuant to the [Epic Games Fan Content Policy](https://www.epicgames.com/site/en-US/fan-art-policy). Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc. All rights reserved by Epic. This material is not official and is not endorsed by Epic. Epic, Epic Games, the Epic Games logo, Fortnite, the Fortnite logo, Unreal, Unreal Engine, the Unreal Engine logo, Unreal Tournament, and the Unreal Tournament logo are trademarks or registered trademarks of Epic Games, Inc. in the United States of America and elsewhere.