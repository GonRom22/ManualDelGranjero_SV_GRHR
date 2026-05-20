package com.example.agendacontactosgrhr.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * DTO para los materiales basado en la base de datos PostgreSQL.
 */
data class MaterialDto(
    val id: Int,
    val nombre: String,
    val fuente: String?,
    @SerializedName("precio_venta")
    val precioVenta: Int,
    val descripcion: String?
)
