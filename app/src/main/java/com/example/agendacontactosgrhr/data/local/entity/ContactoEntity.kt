package com.example.agendacontactosgrhr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla "Contactos" en la base de datos.
 *
 * Cada instancia de esta clase corresponde a una fila de la tabla.
 * Room se encarga de mapear automáticamente esta clase a SQLite.
 */
@Entity(tableName = "contactos",
    indices = [androidx.room.Index(value = ["name"], unique = true)])
data class ContactoEntity ( //Se puede sustituir por NPCEntity o similar para hacer referencia a los personajes del juego
    /**Clave primaria de la tabla.
     *- Se genera automáticamente.
     *- Room asigna el valor al insertar el registro.*/
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    //Elementos comunes de la api y el juego
    val name: String="",
    val thumbnail: String="", //Api
    val thumbnailResId: Int? = null, //imagenes locales de drawable


    //Para adaptarlo a los requisitos de la agenda de stardew valley:
    val estacion: String="",
    val cumpleanos: Int=0, // dia 1 a 28
    val estacionCumpleanos: String="",
    val regalosAmados: String="",
    val regalosOdiados: String="",
    val esSoltero: Boolean=false,
    val habladoHoy: Boolean=false,
    val regaloRecibidoHoy: Boolean=false, //Debe resetearse al final de la semana
    val nivelAmistad: Int = 0, //Cada corazón son 250 puntos (2500 en total)
    val ubicacion: String="",
    val posicion: String="", //Al sur de la alde
)