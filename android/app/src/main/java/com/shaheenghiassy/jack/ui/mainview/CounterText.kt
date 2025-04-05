package com.shaheenghiassy.jack.ui.mainview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaheenghiassy.jack.ui.theme.JACKTheme
import com.shaheenghiassy.jack.usecase.stateToString

@Composable
fun CounterText(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
    val uiState by viewModel.hotRepoState.collectAsStateWithLifecycle(
        initialValue = MainViewUIState.Loading
    )

    Text(
        text = stateToString(uiState),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CounterTextPreview() {
    JACKTheme {
        CounterText()
    }
}
