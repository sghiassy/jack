package com.shaheenghiassy.jack.ui.mainview

import com.shaheenghiassy.jack.data.models.CounterModel

sealed interface MainViewUIState {
    data object Empty : MainViewUIState

    data object Loading : MainViewUIState

    data class Loaded(
        val model: CounterModel
    ) : MainViewUIState
}