package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.APIDatasourceImpl
import com.shaheenghiassy.jack.data.datasources.DiskDatasourceImpl
import com.shaheenghiassy.jack.data.models.CounterModel
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class CounterRepositoryImpl(context : Context): CounterRepository {
    private val diskRepository = DiskDatasourceImpl(context)
    private val apiRepository = APIDatasourceImpl()

    enum class DatasourceType {
        DISK, API
    }

    private var activeDatasource: DatasourceType = DatasourceType.DISK

    private val currentDatasource
        get() = when (activeDatasource) {
            DatasourceType.DISK -> diskRepository
            DatasourceType.API -> apiRepository
        }

    fun switchToDisk() {
        activeDatasource = DatasourceType.DISK
    }

    fun switchToAPI() {
        activeDatasource = DatasourceType.API
    }

    fun setDatasource(type: DatasourceType) {
        activeDatasource = type
    }

    private val _myFlow = MutableSharedFlow<CounterModel>()
    override val myFlow: Flow<CounterModel> = _myFlow.asSharedFlow()

    override suspend fun initialize() {
        val valueFromDisk = currentDatasource.readCounter() ?: 0
        _myFlow.emit(CounterModel(valueFromDisk))
    }

    override suspend fun increment() {
        val valueFromDisk = currentDatasource.readCounter() ?: 0
        val newValue = valueFromDisk + 1
        currentDatasource.writeCounter(newValue)
        _myFlow.emit(CounterModel(newValue))
    }

    override suspend fun decrement() {
        val valueFromDisk = currentDatasource.readCounter() ?: 0
        val newValue = valueFromDisk - 1
        currentDatasource.writeCounter(newValue)
        _myFlow.emit(CounterModel(newValue))
    }

    override suspend fun change(newValue: Int) {
        val valueFromDisk = currentDatasource.readCounter() ?: 0
        val changedValue = valueFromDisk + newValue
        currentDatasource.writeCounter(changedValue)
        _myFlow.emit(CounterModel(changedValue))
    }
}
