package com.example.agendacontactosgrhr.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * DTO para los edificios basado en la base de datos PostgreSQL.
 */
data class EdificioDto(
    val id: Int,
    val nombre: String,
    @SerializedName("tiempo_construccion")
    val tiempoConstruccion: Int,
    @SerializedName("coste_oro")
    val costeOro: Int,
    @SerializedName("cant_madera")
    val cantMadera: Int,
    @SerializedName("cant_piedra")
    val cantPiedra: Int,
    @SerializedName("cant_madera_noble")
    val cantMaderaNoble: Int,
    @SerializedName("cant_fibra")
    val cantFibra: Int,
    @SerializedName("cant_arcilla")
    val cantArcilla: Int,
    @SerializedName("cant_lingote_cobre")
    val cantLingoteCobre: Int,
    @SerializedName("cant_lingote_hierro")
    val cantLingoteHierro: Int,
    @SerializedName("cant_lingote_iridio")
    val cantLingoteIridio: Int,
    @SerializedName("cant_cuarzo_refinado")
    val cantCuarzoRefinado: Int,
    @SerializedName("otros_materiales")
    val otrosMateriales: String?
)
