package com.example.agendacontactosgrhr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla "materiales" en la base de datos local.
 */
@Entity(tableName = "materiales")
data class MaterialEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val fuente: String,
    val precioVenta: Int,
    val descripcion: String
)
