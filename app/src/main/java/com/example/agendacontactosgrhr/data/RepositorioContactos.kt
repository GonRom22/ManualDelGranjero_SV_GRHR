package com.example.agendacontactosgrhr.data

import com.example.agendacontactosgrhr.model.Contacto
import kotlinx.coroutines.delay


/**
 * Repositorio que simula la obtención de contactos..
 *
 * Esta clase se utiliza como una fuente de datos falsa
 * para pruebas, ejemplos o desarrollo inicial.
 *
 * Simula una llamada a una API remota o a una base de datos local.
 */
class RepositorioContactos{

    //Simulamos la carga de datos desde API o BD con unretraso simulado
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