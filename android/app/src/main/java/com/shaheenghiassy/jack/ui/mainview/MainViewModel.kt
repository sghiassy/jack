package com.shaheenghiassy.jack.ui.mainview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, private val counterRepository: CounterRepository): AndroidViewModel(application) {
    val uiState: StateFlow<MainViewUIState> = counterRepository.myFlow
        .map { ctr ->
            MainViewUIState.Loaded(ctr)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainViewUIState.Loading)

    init {
        viewModelScope.launch {
            counterRepository.initialize()
        }
    }

    fun increment() {
        viewModelScope.launch(Dispatchers.IO) {
            counterRepository.increment()
        }
    }

    fun decrement() {
        viewModelScope.launch(Dispatchers.IO) {
            counterRepository.decrement()
        }
    }
}
