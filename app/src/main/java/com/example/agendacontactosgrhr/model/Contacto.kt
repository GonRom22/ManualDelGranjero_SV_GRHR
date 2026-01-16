package com.example.agendacontactosgrhr.model

/**
 * Data class que representa un contacto dentro de la app.
 *
 * Esta clase se utiliza para mostrar y manejar los contactos dentro de la aplicación,
 * sin depender directamente de la base de datos (Room).
 *
 * A diferencia de ContactoEntity, este modelo no tiene que ver con cómo
 * se guarda en la base de datos.
 */
data class Contacto(
    val nombre: String,
    val telefono: String
)