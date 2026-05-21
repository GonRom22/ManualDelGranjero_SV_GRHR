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
    val cantMadera: Int = 0,
    val cantPiedra: Int = 0,
    val cantMaderaNoble: Int = 0,
    val cantFibra: Int = 0,
    val cantArcilla: Int = 0,
    val cantLingoteCobre: Int = 0,
    val cantLingoteHierro: Int = 0,
    val cantLingoteIridio: Int = 0,
    val cantCuarzoRefinado: Int = 0,
    val otrosMateriales: String? = null,
    val materialesNecesarios: String // Resumen de materiales
)
