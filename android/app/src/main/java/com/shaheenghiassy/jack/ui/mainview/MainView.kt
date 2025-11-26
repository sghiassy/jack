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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shaheenghiassy.jack.domain.repository.DatasourceType
import com.shaheenghiassy.jack.ui.components.CounterText
import com.shaheenghiassy.jack.usecase.stateToString

@Composable
fun MainView(
    innerPadding: PaddingValues,
    uiState: MainViewUIState,
    viewModel: MainViewModel
) {
    val currentDatasource by viewModel.currentDatasource.collectAsState()
    val isOperationInProgress by viewModel.isOperationInProgress.collectAsState()

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
                Button(
                    onClick = { viewModel.increment() },
                    enabled = !isOperationInProgress
                ) {
                    Text("Increment")
                }
                Button(
                    onClick = { viewModel.decrement() },
                    enabled = !isOperationInProgress
                ) {
                    Text("Decrement")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Data Source: ${currentDatasource.name}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.switchToDisk() },
                    enabled = !isOperationInProgress,
                    colors = if (currentDatasource == DatasourceType.DISK) {
                        ButtonDefaults.buttonColors()
                    } else {
                        ButtonDefaults.outlinedButtonColors()
                    }
                ) {
                    Text(if (currentDatasource == DatasourceType.DISK) "DISK (Active)" else "DISK")
                }
                Button(
                    onClick = { viewModel.switchToAPI() },
                    enabled = !isOperationInProgress,
                    colors = if (currentDatasource == DatasourceType.API) {
                        ButtonDefaults.buttonColors()
                    } else {
                        ButtonDefaults.outlinedButtonColors()
                    }
                ) {
                    Text(if (currentDatasource == DatasourceType.API) "API (Active)" else "API")
                }
            }
        }
    }
}
