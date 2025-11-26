package com.shaheenghiassy.jack.di

import android.content.Context
import com.shaheenghiassy.jack.app.MyBroadcastReceiver
import com.shaheenghiassy.jack.data.repositories.CounterRepositoryImpl
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCounterRepository(
        @ApplicationContext context: Context,
    ): CounterRepository {
        return CounterRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMyBroadcastReceiver(): MyBroadcastReceiver {
        return MyBroadcastReceiver()
    }
}
