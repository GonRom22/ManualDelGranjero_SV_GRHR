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
    //Nombre del contacto.
    val nombre: String,
    //Teléfono del contacto
    val telefono: String
)