package com.shaheenghiassy.jack.data

import com.shaheenghiassy.jack.CounterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimpleCounterRepository() {
    fun observeCounter(): Flow<CounterModel> {
        return flow {
            emit(CounterModel())
        }
    }
}