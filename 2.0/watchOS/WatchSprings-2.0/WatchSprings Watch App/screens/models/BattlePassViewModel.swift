// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation

@Observable
class BattlePassViewModel
{
    var battlePassResponse: BattlePassResponse? = nil
    var selectedReward: BattlePassReward? = nil
    var selectedTrailerUrl: String? = nil
    var isLoading: Bool = false

    func select(reward: BattlePassReward) {
        selectedReward = reward
    }

    func selectTrailer(url: String) {
        selectedTrailerUrl = url
    }

    func clearTrailer() {
        selectedTrailerUrl = nil
    }

    func refreshBattlePass() {

        isLoading = true

        Task {
            do {
                let result = try await FortniteApiClient.instance.getBattlePassRewards()
                await MainActor.run {
                    self.battlePassResponse = result
                    self.isLoading = false
                    print("Battle Pass loaded with \(result.rewards?.count ?? 0) rewards")
                }
            } catch {
                await MainActor.run {
                    print("Error fetching Battle Pass: \(error.localizedDescription)")
                    self.isLoading = false
                }
            }
        }
    }
}
