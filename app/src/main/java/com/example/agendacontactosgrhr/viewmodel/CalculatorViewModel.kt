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
            try {
                var totalOro = 0
                var totalMadera = 0
                var totalPiedra = 0
                var totalArcilla = 0
                var totalMaderaNoble = 0
                var totalFibra = 0
                var totalLingoteCobre = 0
                var totalLingoteHierro = 0
                var totalLingoteIridio = 0
                var totalCuarzoRefinado = 0
                val detalle = mutableListOf<EdificioDetalleResponse>()

                for (item in items) {
                    val building = edificioRepository.obtenerEdificioPorId(item.id)
                    if (building != null) {
                        totalOro += building.costeOro * item.cantidad
                        totalMadera += building.cantMadera * item.cantidad
                        totalPiedra += building.cantPiedra * item.cantidad
                        totalArcilla += building.cantArcilla * item.cantidad
                        totalMaderaNoble += building.cantMaderaNoble * item.cantidad
                        totalFibra += building.cantFibra * item.cantidad
                        totalLingoteCobre += building.cantLingoteCobre * item.cantidad
                        totalLingoteHierro += building.cantLingoteHierro * item.cantidad
                        totalLingoteIridio += building.cantLingoteIridio * item.cantidad
                        totalCuarzoRefinado += building.cantCuarzoRefinado * item.cantidad
                        
                        detalle.add(EdificioDetalleResponse(
                            nombre = building.nombre,
                            cantidad = item.cantidad
                        ))
                    }
                }

                _buildingResult.value = CalculoEdificiosResponse(
                    totalOro = totalOro,
                    totalMadera = totalMadera,
                    totalPiedra = totalPiedra,
                    totalArcilla = totalArcilla,
                    totalMaderaNoble = totalMaderaNoble,
                    totalFibra = totalFibra,
                    totalLingoteCobre = totalLingoteCobre,
                    totalLingoteHierro = totalLingoteHierro,
                    totalLingoteIridio = totalLingoteIridio,
                    totalCuarzoRefinado = totalCuarzoRefinado,
                    detalle = detalle
                )
            } catch (e: Exception) {
                _error.emit("Error al calcular edificios: ${e.message}")
            }
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
