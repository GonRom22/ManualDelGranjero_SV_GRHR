package com.example.agendacontactosgrhr.data.remote.model

/**
 * Envoltorio para las respuestas de la API de Stardew Valley (Express/Postgres).
 */
data class StardewResponse<T>(
    val total: Int,
    val datos: T,
    val filtros: Map<String, Any>? = null
)
