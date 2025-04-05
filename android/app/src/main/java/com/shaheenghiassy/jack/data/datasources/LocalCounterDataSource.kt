package com.shaheenghiassy.jack.data.datasources

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class LocalCounterDataSource(context: Context) {

    private val file: File = File(context.filesDir, "counter.txt")

    init {
        if (!file.exists()) {
            file.writeText("0")
        }
    }

    suspend fun readCounter(): Int? {
        return withContext(Dispatchers.IO) {
            file.readText().toIntOrNull()
        }
    }

    suspend fun writeCounter(value: Int) {
        withContext(Dispatchers.IO) {
            file.writeText(value.toString())
        }
    }
}
