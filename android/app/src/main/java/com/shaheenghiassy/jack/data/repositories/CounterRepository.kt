package com.shaheenghiassy.jack.data.repositories

import com.shaheenghiassy.jack.data.datasources.LocalCounterDataSource
import com.shaheenghiassy.jack.data.datasources.RemoteCounterDataSource

class CounterRepository(
    private val localDataSource: LocalCounterDataSource,
    private val remoteDataSource: RemoteCounterDataSource
) {

    fun getCounter(): Int {
        return localDataSource.readCounter()
    }

    fun incrementCounter() {
        val currentValue = localDataSource.readCounter()
        val newValue = currentValue + 1
        localDataSource.writeCounter(newValue)
        syncWithRemote(newValue)
    }

    fun decrementCounter() {
        val currentValue = localDataSource.readCounter()
        val newValue = currentValue - 1
        localDataSource.writeCounter(newValue)
        syncWithRemote(newValue)
    }

    private fun syncWithRemote(value: Int) {
        // Check for online status and sync with remote data source
//        remoteDataSource.incrementCounter(value)
    }
}
