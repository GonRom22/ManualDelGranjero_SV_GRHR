package com.example.agendacontactosgrhr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla "edificios" en la base de datos local.
 */
@Entity(tableName = "edificios")
data class EdificioEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val tiempoConstruccion: Int,
    val costeOro: Int,
    val materialesNecesarios: String // Resumen de materiales
)
