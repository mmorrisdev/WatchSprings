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

class StatusViewModel : ViewModel() {

    private val _fortniteComponents = MutableStateFlow<List<StatusComponent>>(emptyList())
    val fortniteComponents = _fortniteComponents.asStateFlow()

    private val _isFortniteOnline = MutableStateFlow(false)
    val isFortniteOnline = _isFortniteOnline.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun refreshStatus() {
        if (_fortniteComponents.value.isNotEmpty()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = MainActivity.fortniteAPI.getServerStatus()
                val components = result.components

                val fortniteGroup = components.find { it.name == "Fortnite" && it.group }

                val fortniteChildren = if (fortniteGroup != null) {
                    _isFortniteOnline.value = fortniteGroup.status == "operational"
                    components.filter { it.groupId == fortniteGroup.id }
                } else {
                    _isFortniteOnline.value = false
                    emptyList()
                }

                _fortniteComponents.value = fortniteChildren

            } catch (e: Exception) {
                _error.value = "Failed to load status: ${e.message}"
                println("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
