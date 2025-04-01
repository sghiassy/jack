package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.LocalCounterDataSource
import com.shaheenghiassy.jack.data.models.CounterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimpleCounterRepository(context: Context) {

    private val localCounter = LocalCounterDataSource(context)

    fun observeCounter(): Flow<CounterModel> {
        return flow {
            val valueFromDisk = localCounter.readCounter()
            val ctr = CounterModel(valueFromDisk ?: -2)
            emit(ctr)
        }
    }
}
