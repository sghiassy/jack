package com.shaheenghiassy.jack.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver: BroadcastReceiver() {
    init {
        Log.d("shizz", "MyBroadcastReceiver instance created")
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("shizz", "i haz cheezburger")
    }

}
