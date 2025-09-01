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

class NewsViewModel : ViewModel()
{
    // keep the mutables private
    private val _selectedNews = MutableStateFlow<News?>(null)
    val selectedNews = _selectedNews.asStateFlow()

    private val _newsResponse = MutableStateFlow<NewsResponse?>(null)
    val newsResponse = _newsResponse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun selectNews(news: News) {
        _selectedNews.value = news
    }

    fun refreshNews() {
        if (_newsResponse.value != null) return // Already loaded

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = MainActivity.fortniteAPI.getBattleRoyaleNews()
                _newsResponse.value = result
                println("News result: $result")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
