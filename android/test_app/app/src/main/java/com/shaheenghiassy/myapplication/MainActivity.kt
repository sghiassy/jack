package com.shaheenghiassy.myapplication

import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shaheenghiassy.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally // Center the contents horizontally
                        ) {
                            Greeting(
                                name = "JACK Test App",
                                modifier = Modifier.padding(innerPadding)
                            )

                            // State variable to hold the text input from the user.
                            var numberInput by remember { mutableStateOf("") }

                            // TextField for number input
                            TextField(
                                value = numberInput,
                                onValueChange = { numberInput = it },
                                label = { Text("Enter a number") }
                            )

                            Button(onClick = {
                                val number = numberInput.toIntOrNull() ?: 1

                                val intent = Intent("CHANGE_VALUE").apply {
                                    setClassName("com.shaheenghiassy.jack", "com.shaheenghiassy.jack.app.MyBroadcastReceiver")
                                    putExtra("count", number)
                                }
                                sendBroadcast(intent)
                                Log.d("shizz", "Button Pressed in Test App with number: $number")
                            }) {
                                Text(text = "Send Broadcast")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
