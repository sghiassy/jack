package com.shaheenghiassy.jack.data.datasources

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.ghiassy.jack.ChangeCounterMutation
import com.ghiassy.jack.CounterQuery
import com.shaheenghiassy.jack.domain.datasource.DiskDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APIDatasourceImpl:DiskDatasource {

    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://jack.ghiassy.com/graphql")
        .build()

    override suspend fun readCounter(): Int? {
        return withContext(Dispatchers.IO) {
            val response = apolloClient.query(CounterQuery()).execute()
            Log.d("shizz", "readCounter ${response.data}")
            response.data?.counter?.value
        }

    }

    override suspend fun writeCounter(value: Int) {
        return withContext(Dispatchers.IO) {
            val response = apolloClient.mutation(ChangeCounterMutation(newValue = value)).execute()
            Log.d("shizz", "writeCounter ${response.data}")
        }
    }

}
