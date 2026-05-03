package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.data.repository.CropRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropsViewModel @Inject constructor(
    private val repository: CropRepository
) : ViewModel() {

    val crops: StateFlow<List<CropEntity>> = repository.obtenerTodosLosCultivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        checkAndLoadMockData()
    }

    private fun checkAndLoadMockData() {
        viewModelScope.launch {
            if (repository.contarCultivos() == 0) {
                repository.insertarCultivos(repository.getMockCrops())
            }
        }
    }
}
