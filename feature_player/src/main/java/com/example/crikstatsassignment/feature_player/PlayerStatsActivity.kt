package com.example.crikstatsassignment.feature_player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.crikstatsassignment.di.FeatureModuleDependencies
import com.example.crikstatsassignment.ui.theme.CrikStatsAssignmentTheme
import dagger.hilt.android.EntryPointAccessors
class PlayerStatsActivity : ComponentActivity() {


    private val viewModelFactory: PlayerStatsViewModelFactory by lazy {
        val dependencies = EntryPointAccessors.fromApplication(
            applicationContext,
            FeatureModuleDependencies::class.java
        )
        PlayerStatsViewModelFactory(dependencies.mockPlayerRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrikStatsAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayerStatsScreen(
                        onBackClicked = { finish() },
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}