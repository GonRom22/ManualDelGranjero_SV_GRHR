package com.example.agendacontactosgrhr.data.remote.datasource

import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import retrofit2.http.GET

/**@ApiService
 * Esta es la interfaz que hará llamadas a la API.
 */

interface ApiService {

    @GET("api/characters")
    suspend fun getCharacters(): List<ContactoEntity>
}
