package com.shaheenghiassy.jack.ui.mainview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaheenghiassy.jack.ui.components.CounterText
import com.shaheenghiassy.jack.usecase.stateToString

@Composable
fun MainView(innerPadding: PaddingValues) {
    val viewModel: MainViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var customNumber by remember { mutableStateOf(0) }

    Box(
        modifier =
            Modifier
                .fillMaxSize() // Make the Box take up the full screen
                .padding(innerPadding),
        // Apply the innerPadding to the Box
        contentAlignment = Alignment.Center, // Center the contents inside the Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center the contents horizontally
        ) {
            CounterText(
                modifier = Modifier.padding(15.dp),
                str = stateToString(uiState),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = customNumber.toString(),
                    onValueChange = { str ->
                        customNumber = str.toIntOrNull() ?: -1
                    },
                    label = { Text("Set Value") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = uiState != MainViewUIState.Loading,
                    modifier = Modifier.width(150.dp),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val newNumber = customNumber.toInt()
                        viewModel.setValue(newNumber)
                    },
                    enabled = uiState != MainViewUIState.Loading,
                ) {
                    Text("Set")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth() // Make the row take up the maximum space horizontally.
                        .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly, // Distribute the buttons evenly
            ) {
                Button(
                    enabled =
                        uiState != MainViewUIState.Loading,
                    onClick = {
                        viewModel.increment()
                    },
                ) {
                    Text("Increment")
                }
                Button(
                    enabled = uiState != MainViewUIState.Loading,
                    onClick = {
                        viewModel.decrement()
                    },
                ) {
                    Text("Decrement")
                }
            }
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth() // Make the row take up the maximum space horizontally.
                        .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly, // Distribute the buttons evenly
            ) {
                Button(
                    enabled = uiState != MainViewUIState.Loading,
                    onClick = {
                        viewModel.switchDatasourceToDisk()
                    },
                ) {
                    Text("Switch to Disk")
                }
                Button(
                    enabled = uiState != MainViewUIState.Loading,
                    onClick = {
                        viewModel.switchDatasourceToAPI()
                    },
                ) {
                    Text("Switch to API")
                }
            }
        }
    }
}
