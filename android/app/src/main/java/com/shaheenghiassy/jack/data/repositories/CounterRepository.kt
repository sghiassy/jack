package com.shaheenghiassy.jack.data.repositories

import com.shaheenghiassy.jack.data.datasources.DiskRepository
import com.shaheenghiassy.jack.data.datasources.RemoteCounterDataSource

class CounterRepository(
    private val localDataSource: DiskRepository,
    private val remoteDataSource: RemoteCounterDataSource
) {

    suspend fun getCounter(): Int? {
        return localDataSource.readCounter()
    }

    suspend fun incrementCounter() {
        localDataSource.readCounter()?.let { currentValue ->
            val newValue = currentValue + 1
            localDataSource.writeCounter(newValue)
            syncWithRemote(newValue)
        }
    }

    suspend fun decrementCounter() {
        localDataSource.readCounter()?.let { currentValue ->
            val newValue = currentValue - 1
            localDataSource.writeCounter(newValue)
            syncWithRemote(newValue)
        }

    }

    private fun syncWithRemote(value: Int) {
        // Check for online status and sync with remote data source
//        remoteDataSource.incrementCounter(value)
    }
}
