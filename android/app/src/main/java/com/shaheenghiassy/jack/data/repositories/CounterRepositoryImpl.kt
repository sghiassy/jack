package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.APIDatasourceImpl
import com.shaheenghiassy.jack.data.datasources.DiskDatasourceImpl
import com.shaheenghiassy.jack.data.models.CounterModel
import com.shaheenghiassy.jack.domain.datasource.DiskDatasource
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class CounterRepositoryImpl(context : Context): CounterRepository {
    private val diskRepository = DiskDatasourceImpl(context)
    private val apiRepository = APIDatasourceImpl()
    private var currentRepository:DiskDatasource = apiRepository

    private val _counterFlow = MutableSharedFlow<CounterModel>()
    override val counterFlow: Flow<CounterModel> = _counterFlow.asSharedFlow()

    override suspend fun initialize() {
        val valueFromDatasource = currentRepository.readCounter() ?: 0
        _counterFlow.emit(CounterModel(valueFromDatasource))
    }

    override suspend fun increment() {
        val valueFromDatasource = currentRepository.readCounter() ?: 0
        val newValue = valueFromDatasource + 1
        currentRepository.writeCounter(newValue)
        _counterFlow.emit(CounterModel(newValue))
    }

    override suspend fun decrement() {
        val valueFromDatasource = currentRepository.readCounter() ?: 0
        val newValue = valueFromDatasource - 1
        currentRepository.writeCounter(newValue)
        _counterFlow.emit(CounterModel(newValue))
    }

    override suspend fun change(newValue: Int) {
        currentRepository.writeCounter(newValue)
        _counterFlow.emit(CounterModel(newValue))
    }

    override suspend fun switchDatasourceToAPI() {
        currentRepository = apiRepository
        val valueFromDatasource = currentRepository.readCounter() ?: 0
        _counterFlow.emit(CounterModel(valueFromDatasource))

    }

    override suspend fun switchDatasourceToDisk() {
        currentRepository = diskRepository
        val valueFromDatasource = currentRepository.readCounter() ?: 0
        _counterFlow.emit(CounterModel(valueFromDatasource))
    }
}
