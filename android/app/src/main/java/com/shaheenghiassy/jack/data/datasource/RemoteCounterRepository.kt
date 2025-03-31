package com.shaheenghiassy.jack.data.datasource

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteCounterDataSource(private val apolloClient: ApolloClient) {

    suspend fun getCounter(): Flow<Int?> = flow {
//        val response = apolloClient.query(GetCounterQuery()).execute()
//        emit(response.data?.counter)
    }

    suspend fun incrementCounter(): Flow<Int?> = flow {
//        val response = apolloClient.mutation(IncrementCounterMutation()).execute()
//        emit(response.data?.incrementCounter)
    }

    suspend fun decrementCounter(): Flow<Int?> = flow {
//        val response = apolloClient.mutation(DecrementCounterMutation()).execute()
//        emit(response.data?.decrementCounter)
    }
}
