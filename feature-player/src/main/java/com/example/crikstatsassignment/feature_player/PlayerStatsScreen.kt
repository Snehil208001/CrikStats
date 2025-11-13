package com.example.crikstatsassignment.feature_player


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PlayerStatsScreen(
    onBackClicked: () -> Unit,
    viewModel: PlayerStatsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Player Stats") },
                navigationIcon = {
                    // This serves as the "Back" button [cite: 59]
                    Button(onClick = onBackClicked) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error != null -> {
                    Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }
                state.playerStats != null -> {
                    PlayerStatsView(stats = state.playerStats!!)
                }
            }
        }
    }
}

@Composable
fun PlayerStatsView(stats: com.example.crikstatsassignment.data.PlayerStats) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Name: ${stats.name}", fontSize = 20.sp) // [cite: 56]
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Matches: ${stats.matches}", fontSize = 20.sp) // [cite: 57]
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Average: ${stats.average}", fontSize = 20.sp) // [cite: 58]
    }
}