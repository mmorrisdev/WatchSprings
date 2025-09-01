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

class ShopViewModel : ViewModel() {
    private val _shopResponse = MutableStateFlow<ShopResponse?>(null)
    val shopResponse = _shopResponse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _selectedItem = MutableStateFlow<ShopItem?>(null)
    val selectedItem = _selectedItem.asStateFlow()

    fun selectItem(item: ShopItem) {
        _selectedItem.value = item
    }

    fun refreshShop(forceReload: Boolean = false) {
        if (!forceReload && _shopResponse.value != null) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = MainActivity.fortniteAPI.getDailyShop()
                _shopResponse.value = result
            } catch (e: Exception) {
                println("Error fetching shop data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

