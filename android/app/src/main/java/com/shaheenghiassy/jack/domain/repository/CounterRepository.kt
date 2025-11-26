package com.shaheenghiassy.jack.domain.repository

import com.shaheenghiassy.jack.data.models.CounterModel
import kotlinx.coroutines.flow.Flow

enum class DatasourceType {
    DISK, API
}

interface CounterRepository {

    val myFlow: Flow<CounterModel>

    suspend fun initialize()

    suspend fun increment()

    suspend fun decrement()

    suspend fun change(newValue:Int)

    fun switchToDisk()

    fun switchToAPI()

    fun setDatasource(type: DatasourceType)

    fun getCurrentDatasource(): DatasourceType
}
