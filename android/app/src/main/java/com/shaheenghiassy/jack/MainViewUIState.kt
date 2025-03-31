package com.shaheenghiassy.jack

sealed interface MainViewUIState {
    data object Empty : MainViewUIState

    data object Loading : MainViewUIState

    data class Loaded(
        val model: CounterModel
    ) : MainViewUIState
}