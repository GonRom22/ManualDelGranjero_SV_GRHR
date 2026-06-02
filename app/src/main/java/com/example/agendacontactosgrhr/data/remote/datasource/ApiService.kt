package com.example.agendacontactosgrhr.data.remote.datasource

import com.example.agendacontactosgrhr.data.remote.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response //import para el JWT

/**
 * Interfaz de Retrofit para la nueva API REST de Stardew Valley.
 */
interface ApiService {

    //Endpoint de autenticación: Envia mail y paswword, si estan bien devuelve el JWT
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    //Endpoint de registro: crea un usuario nuevo. Devuelve 201 + token + usuario (igual que login)
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @GET("api")
    suspend fun healthCheck(): Map<String, String>

    @GET("api/cultivos")
    suspend fun getCultivos(
        @Query("temporada") temporada: String? = null,
        @Query("nombre") nombre: String? = null,
        @Query("orden") orden: String? = null,
        @Query("direccion") direccion: String? = "asc"
    ): StardewResponse<List<CropDto>>

    @GET("api/personajes")
    suspend fun getPersonajes(
        @Query("nombre") nombre: String? = null,
        @Query("temporada") temporada: String? = null,
        @Query("es_soltero") esSoltero: Int? = null,
        @Query("orden") orden: String? = null,
        @Query("direccion") direccion: String? = "asc"
    ): StardewResponse<List<CharacterDto>>

    @GET("api/materiales")
    suspend fun getMateriales(
        @Query("nombre") nombre: String? = null,
        @Query("fuente") fuente: String? = null,
        @Query("orden") orden: String? = null,
        @Query("direccion") direccion: String? = "asc"
    ): StardewResponse<List<MaterialDto>>

    @GET("api/edificios")
    suspend fun getEdificios(
        @Query("nombre") nombre: String? = null,
        @Query("orden") orden: String? = null,
        @Query("direccion") direccion: String? = "asc"
    ): StardewResponse<List<EdificioDto>>

    /**
     * Calculadora de beneficios de cultivos.
     */
    @POST("api/calcular/cultivos")
    suspend fun calcularCultivos(
        @Body request: CalculoCultivosRequest
    ): CalculoCultivosResponse

    /**
     * Calculadora de costes de edificios.
     */
    @POST("api/calcular/edificios")
    suspend fun calcularEdificios(
        @Body request: CalculoEdificiosRequest
    ): CalculoEdificiosResponse
}
