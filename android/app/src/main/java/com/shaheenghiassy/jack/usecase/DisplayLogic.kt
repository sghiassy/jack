package com.shaheenghiassy.jack.usecase

import com.shaheenghiassy.jack.ui.mainview.MainViewUIState

fun stateToString(uiState: MainViewUIState): String {
    val daCount: String = when (uiState) {
        is MainViewUIState.Empty -> "Empty"
        is MainViewUIState.Loading -> "Loading"
        is MainViewUIState.Loaded -> uiState.model.value.toString()
    }
    return daCount
}
