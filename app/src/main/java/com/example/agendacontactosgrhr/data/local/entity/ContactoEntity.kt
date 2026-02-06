package com.example.agendacontactosgrhr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla "Contactos" en la base de datos.
 *
 * Cada instancia de esta clase corresponde a una fila de la tabla.
 * Room se encarga de mapear automáticamente esta clase a SQLite.
 */
@Entity(tableName = "contactos")
data class ContactoEntity ( //Se puede sustituir por NPCEntity o similar para hacer referencia a los personajes del juego
    /**Clave primaria de la tabla.
     *- Se genera automáticamente.
     *- Room asigna el valor al insertar el registro.*/
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    //Elementos comunes de la api y el juego
    val name: String,
    val thumbnail: String,

    //Elementos específicos de la api
    val phone: String,
    val email: String,
    val city: String,
    val country: String,
    val lastName: String,
    val title: String,

    //Para adaptarlo a los requisitos de la agenda de stardew valley:
    val estacion: String,
    val cumpleanos: Int, // dia 1 a 28
    val estacionCumpleanos: String,
    val regalosAmados: String,
    val regalosOdiados: String,
    val esSoltero: Boolean,
    val habladoHoy: Boolean,
    val regaloRecibidoHoy: Boolean, //Debe resetearse al final de la semana
    val nivelAmistad: Int = 0, //Cada corazón son 250 puntos (2500 en total)
    val ubicacion: String,
    val posicion: String, //Al sur de la alde
)