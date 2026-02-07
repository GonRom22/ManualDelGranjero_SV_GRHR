package com.example.agendacontactosgrhr.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.repository.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.agendacontactosgrhr.R

//Creamos una clase ContactoViewModel que hereda de la clase ViewModel().
//ViewModel que gestiona la lista de contactos usando StateFLow
@HiltViewModel
class ContactosViewModel @Inject constructor(
    private val repositorio: ContactoRepository//RepositorioContactos
): ViewModel() {

    //STATE (Estado Persistente)
    //----------------------------
    //MutableStateFLow es un observable que mantiene un estado reactivo y mutable
    //Se declara como privado para seguir el principio de encapsulamiento
    private val _contactos = MutableStateFlow<List<ContactoEntity>>(emptyList())

    //Exponemos el flujo como StateFlow (solo lectura para la UI)
    val contactos: StateFlow<List<ContactoEntity>> = _contactos

    private val _contactoSeleccionado = MutableStateFlow<ContactoEntity?>(null)
    val contactoSeleccionado: StateFlow<ContactoEntity?> = _contactoSeleccionado

    //EVENTS (Eventos Unidireccionales)
    //----------------------------------
    //Creamos un objeto de tipo MutableSharedFlow<String> que emite Strings (en este caso)
    // _eventoUI es el flujo mutable y privado que guarda la dirección de este objeto.
    //MutableSharedFlow no guarda el valor emitido con lo que no es recolectado si la UI no está escuchando
    private val _eventoUI = MutableSharedFlow<String>()

    //SharedFlow público para que la UI escuche los eventos
    val eventoUI: SharedFlow<String> = _eventoUI


    //init se ejecuta cuando se crea una instancia de la clase
    //En este caso, cuando se inicialice la clase ContactoViewModel, se llamará a la función cargarContactos()
    //Simula la carga de datos desde una API o base de datos
    init {
        viewModelScope.launch {
            // Obtenemos los contactos existentes en la DB
            val existentes =
                repositorio.obtenerTodosContactos().first() // first() toma el primer valor emitido

            if (existentes.isEmpty()) {
                // Si no hay contactos, insertamos los por defecto
                val defaultContacts = listOf(
                    ContactoEntity(
                        name = "Gonzalo", estacion = "Invierno", cumpleanos = 10, thumbnailResId = R.drawable.gonzalo//Drawable
                    )
                )

                defaultContacts.forEach {
                    Log.d("DEBUG_CONTACT", "Insertando contacto: ${it.name}, resId=${it.thumbnailResId}")
                    repositorio.insertarContacto(it) }
            }
            //Escuchar cambios den la base de datos
            repositorio.obtenerTodosContactos().collect {
                _contactos.value = it
            }
        }
    }

    //CRUD básico
    fun insertarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.insertarContacto(contacto)
    }

    fun actualizarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.actualizarContacto(contacto)
    }

    fun eliminarContacto(contacto: ContactoEntity) = viewModelScope.launch {
        repositorio.eliminarContacto(contacto)
    }

    fun obtenerContactoPorId(id: Int) = viewModelScope.launch {
        _contactoSeleccionado.value = repositorio.obtenerContactoPorId(id)
    }

    //Aquí la función que se llama cuando el usuario pulse un contacto, lanza un CORRUTINA
    //NO cambia el estado, sino que se emite un evento
    fun onContactoSeleccionado(name: String) =
        viewModelScope.launch {
            //emit es el metodito de SharedFlow que emite un valor al flujo.
            // Es una función suspendida, por ello podemos incluirla dentro de la corrutina
            // viewModelScope.launch.
            _eventoUI.emit("Detalles de $name cargados")
        }


    //Carga de contacto por API
    fun importarStardew() {
        viewModelScope.launch {
            try {
                val nuevoNpc = repositorio.importarUnStardew()
                if (nuevoNpc != null) {
                    Log.d("ContactosViewModel", "NPC insertado: ${nuevoNpc.name}")
                } else {
                    Log.d("ContactosViewModel", "Todos los NPCs ya están en la BBDD")
                }
            } catch (e: Exception) {
                Log.e("ContactosViewModel", "Error al importar NPC: $e")
            }
        }
    }
}
