package com.shaheenghiassy.jack.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.shaheenghiassy.jack.ui.mainview.CounterText
import com.shaheenghiassy.jack.ui.theme.JACKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JACKTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterText(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
