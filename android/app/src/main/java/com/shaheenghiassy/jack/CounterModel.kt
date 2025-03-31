package com.shaheenghiassy.jack

data class CounterModel(val value: Int = 420) {
    fun increment(): CounterModel = copy(value = value + 1)
    fun decrement(): CounterModel = copy(value = value - 1)
}