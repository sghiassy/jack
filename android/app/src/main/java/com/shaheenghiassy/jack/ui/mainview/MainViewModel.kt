package com.shaheenghiassy.jack.ui.mainview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shaheenghiassy.jack.domain.repository.CounterRepository
import com.shaheenghiassy.jack.domain.repository.DatasourceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _currentDatasource = MutableStateFlow(counterRepository.getCurrentDatasource())
    val currentDatasource: StateFlow<DatasourceType> = _currentDatasource.asStateFlow()

    private val _isOperationInProgress = MutableStateFlow(false)
    val isOperationInProgress: StateFlow<Boolean> = _isOperationInProgress.asStateFlow()

    init {
        viewModelScope.launch {
            counterRepository.initialize()
        }
    }

    fun increment() {
        if (_isOperationInProgress.value) return
        viewModelScope.launch(Dispatchers.IO) {
            _isOperationInProgress.value = true
            try {
                counterRepository.increment()
            } finally {
                _isOperationInProgress.value = false
            }
        }
    }

    fun decrement() {
        if (_isOperationInProgress.value) return
        viewModelScope.launch(Dispatchers.IO) {
            _isOperationInProgress.value = true
            try {
                counterRepository.decrement()
            } finally {
                _isOperationInProgress.value = false
            }
        }
    }

    fun switchToDisk() {
        if (_isOperationInProgress.value) return
        _isOperationInProgress.value = true
        counterRepository.switchToDisk()
        _currentDatasource.value = DatasourceType.DISK
        viewModelScope.launch {
            try {
                counterRepository.initialize()
            } finally {
                _isOperationInProgress.value = false
            }
        }
    }

    fun switchToAPI() {
        if (_isOperationInProgress.value) return
        _isOperationInProgress.value = true
        counterRepository.switchToAPI()
        _currentDatasource.value = DatasourceType.API
        viewModelScope.launch {
            try {
                counterRepository.initialize()
            } finally {
                _isOperationInProgress.value = false
            }
        }
    }

    fun setValue(value: Int) {
        if (_isOperationInProgress.value) return
        viewModelScope.launch(Dispatchers.IO) {
            _isOperationInProgress.value = true
            try {
                counterRepository.setValue(value)
            } finally {
                _isOperationInProgress.value = false
            }
        }
    }
}
