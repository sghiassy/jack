package com.shaheenghiassy.jack.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.shaheenghiassy.jack.data.repositories.CounterRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyBroadcastReceiver(): BroadcastReceiver() {

    private val DEFAULT_VALUE = 1
    private val receiverScope = CoroutineScope(Dispatchers.IO)

    init {
        Log.d("shizz", "MyBroadcastReceiver instance created")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context !== null && intent !== null) {
            val counterRepository = CounterRepositoryImpl(context)
            val counterValue = intent.getIntExtra("count", DEFAULT_VALUE)
            receiverScope.launch {
                Log.d("shizz", "i haz cheezburger with value $counterValue")
                counterRepository.change(counterValue)
            }
        }
    }
}
