package com.shaheenghiassy.jack.data.datasources

import android.content.Context
import com.shaheenghiassy.jack.domain.datasource.DiskDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DiskDatasourceImpl(context: Context): DiskDatasource {

    private val file: File = File(context.filesDir, "counter.txt")

    init {
        if (!file.exists()) {
            file.writeText("0")
        }
    }

    override suspend fun readCounter(): Int? {
        return withContext(Dispatchers.IO) {
            file.readText().toIntOrNull()
        }
    }

    override suspend fun writeCounter(value: Int) {
        withContext(Dispatchers.IO) {
            file.writeText(value.toString())
        }
    }
}
