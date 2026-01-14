package com.example.agendacontactosgrhr.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
//View model de DetailScreen
class DetailViewModel : ViewModel(){

    var nombre by mutableStateOf("")
        private set

    fun cargarNombre(nombreRecibido: String?){
        nombre = nombreRecibido ?: "No existe nombre"
    }
}