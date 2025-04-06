package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.DiskRepository
import com.shaheenghiassy.jack.data.models.CounterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class SimpleCounterRepository(context: Context) {
    private val diskRepository = DiskRepository(context)
    private val _myFlow = MutableSharedFlow<CounterModel>()
    val myFlow: Flow<CounterModel> = _myFlow.asSharedFlow()

    suspend fun initialize() {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        _myFlow.emit(CounterModel(valueFromDisk))
    }

    suspend fun increment() {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        val newValue = valueFromDisk + 1
        diskRepository.writeCounter(newValue)
        _myFlow.emit(CounterModel(newValue))
    }

    suspend fun decrement() {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        val newValue = valueFromDisk - 1
        diskRepository.writeCounter(newValue)
        _myFlow.emit(CounterModel(newValue))
    }
}
