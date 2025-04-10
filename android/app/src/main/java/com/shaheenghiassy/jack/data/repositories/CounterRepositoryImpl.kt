package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.DiskDatasourceImpl
import com.shaheenghiassy.jack.data.models.CounterModel
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class CounterRepositoryImpl(context : Context): CounterRepository {
    private val diskRepository = DiskDatasourceImpl(context)
    private val _myFlow = MutableSharedFlow<CounterModel>()
    override val myFlow: Flow<CounterModel> = _myFlow.asSharedFlow()

    override suspend fun initialize() {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        _myFlow.emit(CounterModel(valueFromDisk))
    }

    override suspend fun increment() {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        val newValue = valueFromDisk + 1
        diskRepository.writeCounter(newValue)
        _myFlow.emit(CounterModel(newValue))
    }

    override suspend fun decrement() {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        val newValue = valueFromDisk - 1
        diskRepository.writeCounter(newValue)
        _myFlow.emit(CounterModel(newValue))
    }

    override suspend fun change(newValue: Int) {
        val valueFromDisk = diskRepository.readCounter() ?: 0
        val changedValue = valueFromDisk + newValue
        diskRepository.writeCounter(changedValue)
        _myFlow.emit(CounterModel(changedValue))
    }
}
