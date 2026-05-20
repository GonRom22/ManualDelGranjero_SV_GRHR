package com.example.agendacontactosgrhr.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * DTO para los cultivos basado en la base de datos PostgreSQL.
 */
data class CropDto(
    val id: Int,
    val nombre: String,
    @SerializedName("precio_semilla")
    val precioSemilla: Int,
    @SerializedName("precio_venta")
    val precioVenta: Int,
    @SerializedName("tiempo_crecimiento")
    val tiempoCrecimiento: Int,
    @SerializedName("tiempo_regreso")
    val tiempoRegreso: Int?,
    val temporada: String?
)
