package com.shaheenghiassy.jack.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shaheenghiassy.jack.ui.mainview.MainViewModel
import com.shaheenghiassy.jack.ui.mainview.MainViewUIState
import com.shaheenghiassy.jack.ui.theme.JACKTheme
import com.shaheenghiassy.jack.usecase.stateToString

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JACKTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
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
fun GreetingPreview() {
    JACKTheme {
        Greeting()
    }
}
