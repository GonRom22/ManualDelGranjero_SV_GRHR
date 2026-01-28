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
data class ContactoEntity (
    /**Clave primaria de la tabla.
     *- Se genera automáticamente.
     *- Room asigna el valor al insertar el registro.*/
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    //Nombre del contacto.
    val name: String,
    val lastName: String,
    //Teléfono del contacto
    val phone: String,
    val email: String,
    val city: String,
    val country: String,
    val thumbnail: String

)