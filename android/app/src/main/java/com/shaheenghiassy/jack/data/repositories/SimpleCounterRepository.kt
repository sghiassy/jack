package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.LocalCounterDataSource
import com.shaheenghiassy.jack.data.models.CounterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class SimpleCounterRepository(context: Context) {
    private val localCounter = LocalCounterDataSource(context)
    private var currentCounter: Int = -1

    private val _myFlow = MutableSharedFlow<CounterModel>()
    val myFlow: Flow<CounterModel> = _myFlow.asSharedFlow()

    suspend fun initialize() {
        val valueFromDisk = localCounter.readCounter() ?: 0
        currentCounter = valueFromDisk
        _myFlow.emit(CounterModel(valueFromDisk))
    }

    fun observeCounter(): Flow<CounterModel> {
        return myFlow
    }

    suspend fun increment() {
        currentCounter++
        localCounter.writeCounter(currentCounter)
        val valueFromDisk = localCounter.readCounter() ?: 0
        _myFlow.emit(CounterModel(valueFromDisk))
    }

    suspend fun decrement() {
        currentCounter--
        localCounter.writeCounter(currentCounter)
        val valueFromDisk = localCounter.readCounter() ?: 0
        _myFlow.emit(CounterModel(valueFromDisk))
    }
}
