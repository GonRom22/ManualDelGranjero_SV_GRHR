package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.data.repository.CropRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropsViewModel @Inject constructor(
    private val repository: CropRepository
) : ViewModel() {

    val crops: StateFlow<List<CropEntity>> = repository.obtenerTodosLosCultivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent

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

    /**
     * Sincroniza los cultivos con la API.
     */
    fun syncCrops() {
        viewModelScope.launch {
            val result = repository.syncCropsFromApi()
            if (result.isSuccess) {
                _uiEvent.emit("Cultivos sincronizados correctamente")
            } else {
                _uiEvent.emit("Error al sincronizar: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            val crop = repository.obtenerCultivoPorId(id)
            if (crop != null) {
                repository.actualizarFavorito(id, !crop.isFavorite)
            }
        }
    }
}
