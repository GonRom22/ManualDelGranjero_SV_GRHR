package com.example.agendacontactosgrhr.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.network.NetworkMonitor
import com.example.agendacontactosgrhr.data.repository.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ContactosViewModel @Inject constructor(
    private val repositorio: ContactoRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    val isOnline: StateFlow<Boolean> = networkMonitor.isConnected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val contactos: StateFlow<List<ContactoEntity>> =
        repositorio.obtenerTodosContactos()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _eventoUI = MutableSharedFlow<String>()
    val eventoUI: SharedFlow<String> = _eventoUI

    init {
        cargarMockupSiVacio()
    }

    private fun cargarMockupSiVacio() {
        viewModelScope.launch {
            val actuales = repositorio.obtenerTodosContactos().first()
            if (actuales.isEmpty()) {
                val mockup = repositorio.obtenerNPCsLocales()
                mockup.forEach { repositorio.insertarContacto(it) }
                Log.d("ContactosViewModel", "Mockup cargado: ${mockup.size} NPCs")
            }
        }
    }

    // IMPORTACIÓN MASIVA DESDE LA API
    fun importarTodosLosStardew() {
        viewModelScope.launch {
            if (!isOnline.value) {
                _eventoUI.emit("Sin conexión a internet")
                return@launch
            }

            // Usamos sincronizarNpcs que ya maneja la lógica de red y guardado en DB local
            val result = repositorio.sincronizarNpcs()
            
            if (result.isSuccess) {
                _eventoUI.emit("Sincronización con la API completada")
                Log.d("VIEWMODEL", "NPCs sincronizados correctamente")
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Error desconocido"
                _eventoUI.emit("Error al sincronizar: $errorMsg")
                Log.e("VIEWMODEL", "Error sincronizando NPCs: $errorMsg")
            }
        }
    }

    fun obtenerContactoPorId(id: Int): suspend () -> ContactoEntity? = {
        repositorio.obtenerContactoPorId(id)
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            val contacto = repositorio.obtenerContactoPorId(id)
            if (contacto != null) {
                repositorio.actualizarFavorito(id, !contacto.isFavorite)
            }
        }
    }
}
