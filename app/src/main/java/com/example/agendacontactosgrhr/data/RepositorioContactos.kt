package com.example.agendacontactosgrhr.data

import com.example.agendacontactosgrhr.model.Contacto
import kotlinx.coroutines.delay

class RepositorioContactos{
    //Simulamos la carga de datos desde API o BD
    suspend fun obtenerContactos(): List<Contacto>{
        delay(1500)
        return listOf(
            Contacto("Gonzalo Romero", "+34888888888"),
            Contacto("Héctor Ronquillo", "+34777777777"),
            Contacto("Luke Skywalker", "+34999999999"),
            Contacto("Princesa Leia", "+342222222222"),
            Contacto("Darth Vader", "+34555555555")
        )
    }
}