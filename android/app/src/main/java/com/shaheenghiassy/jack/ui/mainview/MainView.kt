package com.shaheenghiassy.jack.ui.mainview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shaheenghiassy.jack.usecase.stateToString

@Composable
fun MainView(innerPadding: PaddingValues, uiState: MainViewUIState, viewModel: MainViewModel) {
    Column() {
        CounterText(
            modifier = Modifier.padding(innerPadding),
            str = stateToString(uiState)
        )
        Row() {
            Button({
                viewModel.increment()
            }) {
                Text("Increment")
            }
            Button({
                viewModel.decrement()
            }) {
                Text("Decrement")
            }
        }
    }
}
