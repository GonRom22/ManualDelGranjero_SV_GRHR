package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.model.Contacto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//Creamos una clase ContactoViewModel que hereda de la clase ViewModel().
//ViewModel que gestiona la lista de contactos usando StateFLow
class ContactosViewModel : ViewModel() {

    //STATE (Estado Persistente)
    //----------------------------
    //MutableStateFLow es un observable que mantiene un estado reactivo y mutable
    //Se declara como privado para seguir el principio de encapsulamiento
    private val _contactosFLow = MutableStateFlow<List<Contacto>>(emptyList())

    //Exponemos el flujo como StateFlow (solo lectura para la UI)
    val contactosFlow: StateFlow<List<Contacto>> = _contactosFLow

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
        cargarContactos()
    }


    //Función que simula la carga de productos de una API
    private fun cargarContactos() {
        //viewModelScope.launch es la forma de lanzar corrutinas (hilo en segundo plano) desde un ViewModel ligadas al ciclo de vida del ViewModel.
        //Todas las corrutinas lanzada dentro de este scope serán canceladas cuando el ViewModel sea destruido.
        //Aquí estamos simulando traer la información de la API y guardarla en la base de datos local
        viewModelScope.launch {
            delay(1500)//Esto simula un tiempo de llamada a la red o base de datos
            _contactosFLow.value = listOf(
                Contacto("Gonzalo Romero", "+34888888888"),
                Contacto("Héctor Ronquillo", "+34777777777"),
                Contacto("Luke Skywalker", "+34999999999"),
                Contacto("Princesa Leia", "+342222222222"),
                Contacto("Darth Vader", "+34555555555")
            )
        }
    }

    //Aquí la función que se llama cuando el usuario pulse un contacto, lanza un CORRUTINA
    //NO cambia el estado, sino que se emite un evento
    fun onContactoSeleccionado(nombre: String){
        viewModelScope.launch{
            //emit es el metodito de SharedFlow que emite un valor al flujo.
            // Es una función suspendida, por ello podemos incluirla dentro de la corrutina
            // viewModelScope.launch.
            _eventoUI.emit("Detalles de $nombre cargados")
        }
    }
}