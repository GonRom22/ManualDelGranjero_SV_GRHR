package com.example.agendacontactosgrhr.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * DTOs para los cálculos de cultivos.
 */

data class CultivoCalculo(
    val id: Int,
    val cantidad: Int,
    val cosechas: Int
)

data class CalculoCultivosRequest(
    val cultivos: List<CultivoCalculo>
)

data class CultivoDetalleResponse(
    val nombre: String,
    val cantidad: Int,
    val cosechas: Int,
    @SerializedName("precio_semilla")
    val precioSemilla: Int,
    @SerializedName("precio_venta")
    val precioVenta: Int,
    @SerializedName("ganancia_bruta")
    val gananciaBruta: Int,
    @SerializedName("ganancia_neta")
    val gananciaNeta: Int
)

data class CalculoCultivosResponse(
    @SerializedName("ganancia_total")
    val gananciaTotal: Int,
    @SerializedName("coste_semillas")
    val costeSemillas: Int,
    @SerializedName("beneficio_neto")
    val beneficioNeto: Int,
    val detalle: List<CultivoDetalleResponse>
)

/**
 * DTOs para los cálculos de edificios.
 */

data class EdificioCalculo(
    val id: Int,
    val cantidad: Int
)

data class CalculoEdificiosRequest(
    val edificios: List<EdificioCalculo>
)

data class CalculoEdificiosResponse(
    @SerializedName("total_oro")
    val totalOro: Int,
    @SerializedName("total_madera")
    val totalMadera: Int,
    @SerializedName("total_piedra")
    val totalPiedra: Int,
    @SerializedName("total_arcilla")
    val totalArcilla: Int,
    @SerializedName("total_madera_noble")
    val totalMaderaNoble: Int,
    @SerializedName("total_lingote_hierro")
    val totalLingoteHierro: Int,
    @SerializedName("total_lingote_iridio")
    val totalLingoteIridio: Int,
    val detalle: List<EdificioDetalleResponse>
)

data class EdificioDetalleResponse(
    val nombre: String,
    val cantidad: Int
)
