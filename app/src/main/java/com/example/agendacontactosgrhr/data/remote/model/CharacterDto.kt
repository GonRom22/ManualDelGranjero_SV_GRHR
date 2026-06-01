package com.example.agendacontactosgrhr.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * DTO para los personajes (NPCs) basado en la base de datos PostgreSQL.
 */
data class CharacterDto(
    val id: Int,
    val nombre: String,
    @SerializedName("temporada_cumpleanos")
    val temporadaCumpleanos: String?,
    @SerializedName("dia_cumpleanos")
    val diaCumpleanos: Int?,
    @SerializedName("es_soltero")
    val esSoltero: Int, // 0 para no, 1 para sí (Postgres SmallInt)
    @SerializedName("regalos_amados")
    val regalosAmados: String?,
    @SerializedName("regalos_odiados")
    val regalosOdiados: String?,
    @SerializedName("imagen_url")
    val imagenUrl: String?
)
