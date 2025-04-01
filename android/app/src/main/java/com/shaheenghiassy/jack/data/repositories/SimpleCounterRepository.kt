package com.shaheenghiassy.jack.data.repositories

import com.shaheenghiassy.jack.data.models.CounterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimpleCounterRepository() {
    fun observeCounter(): Flow<CounterModel> {
        return flow {
            emit(CounterModel())
        }
    }
}