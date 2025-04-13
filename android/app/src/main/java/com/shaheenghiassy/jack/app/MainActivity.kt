package com.shaheenghiassy.jack.app

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo.ApolloClient
import com.ghiassy.jack.GamesQuery
import com.shaheenghiassy.jack.ui.mainview.MainView
import com.shaheenghiassy.jack.ui.mainview.MainViewModel
import com.shaheenghiassy.jack.ui.theme.JACKTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject lateinit var myBroadcastReceiver: MyBroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val filter = IntentFilter("CHANGE_VALUE")
        registerReceiver(myBroadcastReceiver, filter, RECEIVER_EXPORTED)
        setContent {
            JACKTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: MainViewModel = viewModel()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    MainView(innerPadding, uiState, viewModel)



                    val apolloClient = ApolloClient.Builder()
                        .serverUrl("https://jack.ghiassy.com/graphql")
                        .build()
                    LaunchedEffect(Unit) {
                        val response = apolloClient.query(GamesQuery()).execute()
                        Log.d("GamesQuery", "Success ${response.data}")
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("shizz", "MyBroadcastReceiver is being unregistered")
        unregisterReceiver(myBroadcastReceiver)
    }
}
