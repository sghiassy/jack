package com.shaheenghiassy.jack.ui.mainview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shaheenghiassy.jack.data.repositories.SimpleCounterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val counterRepository = SimpleCounterRepository(application.applicationContext)
    val uiState: StateFlow<MainViewUIState> = counterRepository.observeCounter()
        .map { ctr ->
            MainViewUIState.Loaded(ctr.increment())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainViewUIState.Loading)
}
