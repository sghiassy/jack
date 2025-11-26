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

    fun switchToDisk() {
        counterRepository.switchToDisk()
        _currentDatasource.value = DatasourceType.DISK
        viewModelScope.launch {
            counterRepository.initialize()
        }
    }

    fun switchToAPI() {
        counterRepository.switchToAPI()
        _currentDatasource.value = DatasourceType.API
        viewModelScope.launch {
            counterRepository.initialize()
        }
    }
}
