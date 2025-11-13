package com.example.crikstatsassignment.feature_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crikstatsassignment.data.MockPlayerRepository
import com.example.crikstatsassignment.data.PlayerStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayerStatsUiState(
    val isLoading: Boolean = true,
    val playerStats: PlayerStats? = null,
    val error: String? = null
)

@HiltViewModel
class PlayerStatsViewModel @Inject constructor(
    private val repository: MockPlayerRepository // Injected from :app module
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