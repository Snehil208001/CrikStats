package com.example.crikstatsassignment.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onNavigateToPlayerStats: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // When download is complete, navigate
    if (state.isDownloadComplete) {
        onNavigateToPlayerStats()
        viewModel.onNavigationComplete() // Reset state
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.loadingText)
            } else {
                Button(
                    onClick = { viewModel.startModuleDownload() }, // [cite: 75]
                    enabled = !state.isModuleInstalled
                ) {
                    Text(
                        text = if (state.isModuleInstalled) "Player Stats Module Installed"
                        else "Download Player Stats Module" // [cite: 52]
                    )
                }
                if (state.isModuleInstalled) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onNavigateToPlayerStats) {
                        Text(text = "View Player Stats")
                    }
                }
                if (state.error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Error: ${state.error}")
                }
            }
        }
    }
}