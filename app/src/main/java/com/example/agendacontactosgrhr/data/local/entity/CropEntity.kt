package com.example.agendacontactosgrhr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivos")
data class CropEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precioSemilla: Int,
    val precioVenta: Int,
    val tiempoCrecimiento: Int,
    val tiempoRegreso: Int,
    val temporada: String
)
