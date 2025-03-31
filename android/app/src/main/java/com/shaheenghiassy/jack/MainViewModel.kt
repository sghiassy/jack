package com.shaheenghiassy.jack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shaheenghiassy.jack.data.SimpleCounterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val counterRepository = SimpleCounterRepository()
    val uiState: Flow<MainViewUIState.Loaded> = counterRepository.observeCounter()
        .map { ctr ->
            MainViewUIState.Loaded(ctr.increment())
        }
}