package com.shaheenghiassy.jack.domain.repository

import com.shaheenghiassy.jack.data.models.CounterModel
import kotlinx.coroutines.flow.Flow

interface CounterRepository {

    val myFlow: Flow<CounterModel>

    suspend fun initialize()

    suspend fun increment()

    suspend fun decrement()

    suspend fun change(newValue:Int)
}
