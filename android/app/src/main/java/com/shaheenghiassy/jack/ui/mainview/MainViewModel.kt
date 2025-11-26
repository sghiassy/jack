package com.shaheenghiassy.jack.ui.mainview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        application: Application,
        private val counterRepository: CounterRepository,
    ) : AndroidViewModel(application) {
        private val _uiState = MutableStateFlow<MainViewUIState>(MainViewUIState.Loading)
        val uiState: StateFlow<MainViewUIState> = _uiState.asStateFlow()

        init {
            // Setup collector
            viewModelScope.launch {
                counterRepository.counterFlow.collect { counterModel ->
                    _uiState.value = MainViewUIState.Loaded(counterModel)
                }
            }

            // Grab initial values
            viewModelScope.launch {
                counterRepository.initialize()
            }
        }

        fun setValue(newNumber: Int) {
            _uiState.value = MainViewUIState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                counterRepository.change(newNumber)
            }
        }

        fun increment() {
            _uiState.value = MainViewUIState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                counterRepository.increment()
            }
        }

        fun decrement() {
            _uiState.value = MainViewUIState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                counterRepository.decrement()
            }
        }

        fun switchDatasourceToDisk() {
            viewModelScope.launch(Dispatchers.IO) {
                counterRepository.switchDatasourceToDisk()
            }
        }

        fun switchDatasourceToAPI() {
            viewModelScope.launch(Dispatchers.IO) {
                counterRepository.switchDatasourceToAPI()
            }
        }
    }
