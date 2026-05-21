package com.example.agendacontactosgrhr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos",
    indices = [androidx.room.Index(value = ["name"], unique = true)])
data class ContactoEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String="",
    val thumbnail: String="",
    val thumbnailResId: Int? = null,
    val estacion: String="",
    val cumpleanos: Int=0,
    val estacionCumpleanos: String="",
    val regalosAmados: String="",
    val regalosOdiados: String="",
    val esSoltero: Boolean=false,
    val habladoHoy: Boolean=false,
    val regaloRecibidoHoy: Boolean=false,
    val regalosSemanales: Int = 0, // Nuevo campo para contar regalos de 0 a 2
    val nivelAmistad: Int = 0,
    val ubicacion: String="",
    val posicion: String="",
    val isFavorite: Boolean = false
)
