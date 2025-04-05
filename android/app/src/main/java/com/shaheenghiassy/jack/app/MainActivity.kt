package com.shaheenghiassy.jack.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaheenghiassy.jack.ui.mainview.MainView
import com.shaheenghiassy.jack.ui.mainview.MainViewModel
import com.shaheenghiassy.jack.ui.theme.JACKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JACKTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: MainViewModel = viewModel()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    MainView(innerPadding, uiState, viewModel)
                }
            }
        }
    }
}
