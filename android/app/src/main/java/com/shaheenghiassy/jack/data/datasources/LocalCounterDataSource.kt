package com.shaheenghiassy.jack.data.datasources

import android.content.Context
import java.io.File

class LocalCounterDataSource(private val context: Context) {

    private val file: File = File(context.filesDir, "counter.txt")

    init {
        if (!file.exists()) {
            file.writeText("0")
        }
    }

    fun readCounter(): Int {
        return file.readText().toIntOrNull() ?: 0
    }

    fun writeCounter(value: Int) {
        file.writeText(value.toString())
    }
}
