package com.shaheenghiassy.jack.domain.datasource

interface DiskDatasource {
    suspend fun readCounter(): Int?

    suspend fun writeCounter(value: Int)
}
