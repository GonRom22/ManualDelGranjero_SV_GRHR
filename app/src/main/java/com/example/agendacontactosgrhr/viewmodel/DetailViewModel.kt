package com.example.agendacontactosgrhr.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
//View model de DetailScreen
class DetailViewModel : ViewModel(){

    var telefono by mutableStateOf("")
        private set

    fun cargarNombre(nombreRecibido: String?){
        telefono = nombreRecibido ?: "No existe nombre"
    }
}