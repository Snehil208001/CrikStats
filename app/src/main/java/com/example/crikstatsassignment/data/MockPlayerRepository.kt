package com.example.crikstatsassignment.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockPlayerRepository @Inject constructor() {

    // Simulates a network call
    suspend fun getPlayerStats(): PlayerStats {
        delay(1000)
        return PlayerStats(
            name = "Virat Kohli",
            matches = 253,
            average = 57.8
        )
    }
}