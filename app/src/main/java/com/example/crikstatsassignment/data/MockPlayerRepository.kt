package com.example.crikstatsassignment.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

// We use @Singleton to ensure the same instance is shared
@Singleton
class MockPlayerRepository @Inject constructor() {

    // Simulates a network call
    suspend fun getPlayerStats(): PlayerStats {
        delay(1000) // Simulate network latency
        return PlayerStats(
            name = "Virat Kohli", // [cite: 56]
            matches = 253,        // [cite: 57]
            average = 57.8        // [cite: 58]
        )
    }
}