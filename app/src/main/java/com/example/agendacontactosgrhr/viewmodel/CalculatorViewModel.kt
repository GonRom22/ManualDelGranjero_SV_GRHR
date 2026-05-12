package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.data.local.entity.EdificioEntity
import com.example.agendacontactosgrhr.data.remote.model.*
import com.example.agendacontactosgrhr.data.repository.CalculatorRepository
import com.example.agendacontactosgrhr.data.repository.CropRepository
import com.example.agendacontactosgrhr.data.repository.EdificioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculatorRepository: CalculatorRepository,
    private val cropRepository: CropRepository,
    private val edificioRepository: EdificioRepository
) : ViewModel() {

    // Datos para las listas de selección
    val availableCrops: StateFlow<List<CropEntity>> = cropRepository.obtenerTodosLosCultivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableBuildings: StateFlow<List<EdificioEntity>> = edificioRepository.obtenerTodosLosEdificios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Estado para la calculadora de cultivos
    private val _cropResult = MutableStateFlow<CalculoCultivosResponse?>(null)
    val cropResult: StateFlow<CalculoCultivosResponse?> = _cropResult

    // Estado para la calculadora de edificios
    private val _buildingResult = MutableStateFlow<CalculoEdificiosResponse?>(null)
    val buildingResult: StateFlow<CalculoEdificiosResponse?> = _buildingResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    fun calcularCultivos(items: List<CultivoCalculo>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Calcular localmente en lugar de usar API
                var gananciaTotal = 0
                var costeSemillas = 0
                val detalle = mutableListOf<CultivoDetalleResponse>()

                for (item in items) {
                    val crop = cropRepository.obtenerCultivoPorId(item.id)
                    if (crop != null) {
                        val gananciaBruta = item.cantidad * crop.precioVenta * item.cosechas
                        val coste = item.cantidad * crop.precioSemilla
                        val gananciaNeta = gananciaBruta - coste

                        gananciaTotal += gananciaBruta
                        costeSemillas += coste

                        detalle.add(CultivoDetalleResponse(
                            nombre = crop.nombre,
                            cantidad = item.cantidad,
                            cosechas = item.cosechas,
                            precioSemilla = crop.precioSemilla,
                            precioVenta = crop.precioVenta,
                            gananciaBruta = gananciaBruta,
                            gananciaNeta = gananciaNeta
                        ))
                    }
                }

                val beneficioNeto = gananciaTotal - costeSemillas
                _cropResult.value = CalculoCultivosResponse(
                    gananciaTotal = gananciaTotal,
                    costeSemillas = costeSemillas,
                    beneficioNeto = beneficioNeto,
                    detalle = detalle
                )
            } catch (e: Exception) {
                _error.emit("Error al calcular cultivos: ${e.message}")
            }
            _isLoading.value = false
        }
    }

    fun calcularEdificios(items: List<EdificioCalculo>) {
        viewModelScope.launch {
            _isLoading.value = true
            // Since API is not available, emit error
            _error.emit("Cálculo de edificios no disponible (API no implementada)")
            _isLoading.value = false
        }
    }

    fun syncBuildings() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = edificioRepository.sincronizarEdificios()
            if (result.isSuccess) {
                _error.emit("Edificios sincronizados correctamente")
            } else {
                _error.emit("Error al sincronizar edificios: ${result.exceptionOrNull()?.message}")
            }
            _isLoading.value = false
        }
    }
}
