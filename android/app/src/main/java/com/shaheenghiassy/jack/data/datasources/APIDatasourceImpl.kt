package com.shaheenghiassy.jack.data.datasources

import android.content.Context
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.httpCache
import com.apollographql.apollo.cache.http.httpDoNotStore
import com.ghiassy.jack.ChangeCounterMutation
import com.ghiassy.jack.CounterQuery
import com.shaheenghiassy.jack.domain.datasource.DiskDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class APIDatasourceImpl(context: Context):DiskDatasource {

    private val cacheFile: File = File(context.filesDir, "apolloCache")

    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://jack.ghiassy.com/graphql")
        .httpCache(
            directory = cacheFile, // Use a dedicated directory for the cache
            maxSize = 100 * 1024 * 1024 // Configure a max size of 100MB
        )
        .build()

    override suspend fun readCounter(): Int? {
        return withContext(Dispatchers.IO) {
            val response = apolloClient
                                .query(CounterQuery())
//                                .httpFetchPolicy(HttpFetchPolicy.NetworkFirst) // Try the network first - if there's an error, try the cache
//                                .httpExpireTimeout(60 * 60 * 1000) // Expire after 1 hour
                                .httpDoNotStore(httpDoNotStore = true)
                                .execute()
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
