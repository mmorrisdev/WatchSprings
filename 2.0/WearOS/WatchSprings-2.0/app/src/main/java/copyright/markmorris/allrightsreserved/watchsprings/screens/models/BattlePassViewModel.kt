// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BattlePassViewModel : ViewModel()
{
    private val _selectedTrailerUrl = MutableStateFlow<String?>(null)
    val selectedTrailerUrl = _selectedTrailerUrl.asStateFlow()

    fun selectTrailer(url: String) {
        _selectedTrailerUrl.value = url
    }

    fun clearTrailer() {
        _selectedTrailerUrl.value = null
    }

    private val _battlePassResponse = MutableStateFlow<BattlePassResponse?>(null)
    val battlePassResponse = _battlePassResponse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _selectedReward = MutableStateFlow<BattlePassReward?>(null)
    val selectedReward = _selectedReward.asStateFlow()

    fun selectReward(reward: BattlePassReward) {
        _selectedReward.value = reward
    }

    fun refreshBattlePass(forceReload: Boolean = false) {
        if (!forceReload && _battlePassResponse.value != null) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = MainActivity.fortniteAPI.getBattlePassRewards()
                _battlePassResponse.value = result
            } catch (e: Exception) {
                println("Error fetching battle pass data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
