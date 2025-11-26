package com.shaheenghiassy.jack.data.repositories

import android.content.Context
import com.shaheenghiassy.jack.data.datasources.APIDatasourceImpl
import com.shaheenghiassy.jack.data.datasources.DiskDatasourceImpl
import com.shaheenghiassy.jack.data.models.CounterModel
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import com.shaheenghiassy.jack.domain.repository.DatasourceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class CounterRepositoryImpl(context : Context): CounterRepository {
    private val diskRepository = DiskDatasourceImpl(context)
    private val apiRepository = APIDatasourceImpl()

    private var activeDatasource: DatasourceType = DatasourceType.DISK

    private val currentDatasource
        get() = when (activeDatasource) {
            DatasourceType.DISK -> diskRepository
            DatasourceType.API -> apiRepository
        }

    override fun switchToDisk() {
        activeDatasource = DatasourceType.DISK
    }

    override fun switchToAPI() {
        activeDatasource = DatasourceType.API
    }

    override fun setDatasource(type: DatasourceType) {
        activeDatasource = type
    }

    override fun getCurrentDatasource(): DatasourceType {
        return activeDatasource
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

    override suspend fun setValue(value: Int) {
        currentDatasource.writeCounter(value)
        _myFlow.emit(CounterModel(value))
    }
}
