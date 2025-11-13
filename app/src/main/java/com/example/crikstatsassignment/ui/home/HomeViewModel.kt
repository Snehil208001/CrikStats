package com.example.crikstatsassignment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val loadingText: String = "",
    val error: String? = null,
    val isModuleInstalled: Boolean = false,
    val isDownloadComplete: Boolean = false
)

private const val FEATURE_PLAYER_NAME = "feature_player"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val splitInstallManager: SplitInstallManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var mySessionId = 0

    private val listener = SplitInstallStateUpdatedListener { state ->
        if (state.sessionId() == mySessionId) {
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    val progress = (state.bytesDownloaded() * 100 / state.totalBytesToDownload()).toInt()
                    _uiState.update { it.copy(isLoading = true, loadingText = "Downloading... $progress%") }
                }
                SplitInstallSessionStatus.INSTALLING -> {
                    _uiState.update { it.copy(isLoading = true, loadingText = "Installing...") }
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isDownloadComplete = true,
                            isModuleInstalled = true
                        )
                    }
                }
                SplitInstallSessionStatus.FAILED -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Download failed: ${state.errorCode()}")
                    }
                }
                SplitInstallSessionStatus.PENDING -> {
                    _uiState.update { it.copy(isLoading = true, loadingText = "Download pending...") }
                }
                else -> { /* Handle other states */ }
            }
        }
    }

    init {
        splitInstallManager.registerListener(listener)
        checkIfModuleIsInstalled()
    }

    private fun checkIfModuleIsInstalled() {
        val isInstalled = splitInstallManager.installedModules.contains(FEATURE_PLAYER_NAME)
        _uiState.update { it.copy(isModuleInstalled = isInstalled) }
    }

    fun startModuleDownload() {
        _uiState.update { it.copy(isLoading = true, loadingText = "Starting download...") }

        val request = SplitInstallRequest.newBuilder()
            .addModule(FEATURE_PLAYER_NAME)
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener { sessionId ->
                mySessionId = sessionId
            }
            .addOnFailureListener { exception ->
                _uiState.update { it.copy(isLoading = false, error = exception.message) }
            }
    }

    fun onNavigationComplete() {
        _uiState.update { it.copy(isDownloadComplete = false) }
    }

    override fun onCleared() {
        splitInstallManager.unregisterListener(listener)
        super.onCleared()
    }
}