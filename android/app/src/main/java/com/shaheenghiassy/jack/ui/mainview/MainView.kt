package com.shaheenghiassy.jack.ui.mainview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shaheenghiassy.jack.ui.components.CounterText
import com.shaheenghiassy.jack.usecase.stateToString

@Composable
fun MainView(
    innerPadding: PaddingValues,
    uiState: MainViewUIState,
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize() // Make the Box take up the full screen
            .padding(innerPadding), // Apply the innerPadding to the Box
        contentAlignment = Alignment.Center // Center the contents inside the Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // Center the contents horizontally
        ) {
            CounterText(
                modifier = Modifier.padding(15.dp),
                str = stateToString(uiState),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Make the row take up the maximum space horizontally.
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribute the buttons evenly
            ) {
                Button(onClick = {
                    viewModel.increment()
                }) {
                    Text("Increment")
                }
                Button(onClick = {
                    viewModel.decrement()
                }) {
                    Text("Decrement")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Make the row take up the maximum space horizontally.
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribute the buttons evenly
            ) {
                Button(onClick = {
                    viewModel.switchDatasourceToDisk()
                }) {
                    Text("Switch to Disk")
                }
                Button(onClick = {
                    viewModel.switchDatasourceToAPI()
                }) {
                    Text("Switch to API")
                }
            }
        }
    }
}
