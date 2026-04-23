package com.example.agendacontactosgrhr.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.network.NetworkMonitor
import com.example.agendacontactosgrhr.data.repository.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject //Antes jakarta, pero no funcionaba porque entraba en conflicto con Hilt
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.agendacontactosgrhr.R
import kotlinx.coroutines.flow.map

//Creamos una clase ContactoViewModel que hereda de la clase ViewModel().
//ViewModel que gestiona la lista de contactos usando StateFLow
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
        viewModelScope.launch {

            repositorio.obtenerTodosContactos().collect { lista ->

                // 🔥 AUTO IMPORTACIÓN SI LA BD ESTÁ VACÍA
                if (lista.isEmpty() && isOnline.value) {
                    Log.d("VIEWMODEL", "BD vacía → importando NPCs")

                    try {
                        importarTodosLosStardew()
                    } catch (e: Exception) {
                        Log.e("VIEWMODEL", "Error importando NPCs: $e")
                    }
                }
            }
        }
    }

    // IMPORTACIÓN MASIVA
    fun importarTodosLosStardew() {
        viewModelScope.launch {

            if (!isOnline.value) {
                _eventoUI.emit("Sin conexión a internet")
                return@launch
            }

            try {
                val existentes = repositorio.obtenerTodosContactos().first()
                val apiNpcs = repositorio.importarTodosDesdeApi()

                val nuevos = apiNpcs.filter { apiNpc ->
                    existentes.none { it.name == apiNpc.name }
                }

                nuevos.forEach {
                    repositorio.insertarContacto(it)
                }

                Log.d("VIEWMODEL", "NPCs importados: ${nuevos.size}")

            } catch (e: Exception) {
                Log.e("VIEWMODEL", "Error importando NPCs", e)
            }
        }
    }

    fun obtenerContactoPorId(id: Int): suspend () -> ContactoEntity? = {
        repositorio.obtenerContactoPorId(id)
    }
}
