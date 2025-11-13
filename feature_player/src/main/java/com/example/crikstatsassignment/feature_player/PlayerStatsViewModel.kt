package com.example.crikstatsassignment.feature_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.crikstatsassignment.data.MockPlayerRepository
import com.example.crikstatsassignment.data.PlayerStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlayerStatsUiState(
    val isLoading: Boolean = true,
    val playerStats: PlayerStats? = null,
    val error: String? = null
)

class PlayerStatsViewModel(
    private val repository: MockPlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerStatsUiState())
    val uiState: StateFlow<PlayerStatsUiState> = _uiState.asStateFlow()

    init {
        fetchPlayerStats()
    }

    private fun fetchPlayerStats() {
        viewModelScope.launch {
            try {
                val stats = repository.getPlayerStats()
                _uiState.update { it.copy(isLoading = false, playerStats = stats) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

class PlayerStatsViewModelFactory(
    private val repository: MockPlayerRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerStatsViewModel::class.java)) {
            return PlayerStatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}