package com.example.agendacontactosgrhr.data.remote.datasource

import com.example.agendacontactosgrhr.data.remote.model.ApiResponse
import retrofit2.http.GET

/**Esta es la interfaz que hará llamadas a la API
 *
 */
interface ApiService{
    @GET(value = "?inc=name,location,picture")

    suspend fun getContact(): ApiResponse
}