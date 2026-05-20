package com.example.agendacontactosgrhr.data.remote.model

/**
 * Envoltorio genérico para las respuestas de la API.
 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T,
    val message: String
)
