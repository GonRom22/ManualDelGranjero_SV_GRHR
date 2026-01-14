package com.example.agendacontactosgrhr.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agendacontactosgrhr.model.Contacto

//Creamos una clase ContactoViewModel que hereda de la clase ViewModel()
class ContactosViewModel : ViewModel() {

    var contactos by mutableStateOf<List<Contacto>>(emptyList())
        private set

    //init se ejecuta cuando se crea una instancia de la clase
    //En este caso, cuando se inicialice la clase ContactoViewModel, se llamará a la función cargarContactos()
    init {
        cargarContactos()
    }


    //Función que simula la carga de productos de una API
    private fun cargarContactos() {
        contactos = listOf(
            Contacto("Gonzalo Romero", "+34888888888"),
            Contacto("Héctor Ronquillo", "+34777777777"),
            Contacto("Luke Skywalker", "+34999999999"),
            Contacto("Princesa Leia", "+342222222222"),
            Contacto("Darth Vader", "+34555555555")
        )
    }
}