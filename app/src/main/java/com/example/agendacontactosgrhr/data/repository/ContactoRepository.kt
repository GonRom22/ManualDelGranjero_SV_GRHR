package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService
import com.example.agendacontactosgrhr.R
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio encargado de gestionar el acceso a los datos de Contactos.
 */
@Singleton
class ContactoRepository @Inject constructor(
    private val contactoDao: ContactoDao,
    private val dataSource: ApiService
) {

    fun obtenerTodosContactos(): Flow<List<ContactoEntity>> =
        contactoDao.obtenerTodosContactos()

    suspend fun insertarContacto(contacto: ContactoEntity) =
        contactoDao.insertarContacto(contacto)

    suspend fun actualizarContacto(contacto: ContactoEntity) =
        contactoDao.actualizarContacto(contacto)

    suspend fun eliminarContacto(contacto: ContactoEntity) =
        contactoDao.eliminarContacto(contacto)

    suspend fun obtenerContactoPorId(id: Int): ContactoEntity? =
        contactoDao.obtenerContactoPorId(id)

    //NUEVO: traer TODOS los NPCs desde API
    suspend fun importarTodosDesdeApi(): List<ContactoEntity> {
        return try {
            val response = dataSource.getCharacters()

            response.map {
                ContactoEntity(
                    name = it.name ?: "",
                    thumbnail = it.image ?: ""
                )
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

    // Mockup data based on local drawables
    fun obtenerNPCsLocales(): List<ContactoEntity> {
        return listOf(
            ContactoEntity(name = "Abigail", ubicacion = "Pierre's General Store", thumbnailResId = R.drawable.abigail),
            ContactoEntity(name = "Alex", ubicacion = "1 River Road", thumbnailResId = R.drawable.alex),
            ContactoEntity(name = "Elliott", ubicacion = "Elliott's Cabin", thumbnailResId = R.drawable.elliott),
            ContactoEntity(name = "Emily", ubicacion = "2 Willow Lane", thumbnailResId = R.drawable.emily),
            ContactoEntity(name = "Gonzalo", ubicacion = "Desconocido", thumbnailResId = R.drawable.gonzalo),
            ContactoEntity(name = "Haley", ubicacion = "2 Willow Lane", thumbnailResId = R.drawable.haley),
            ContactoEntity(name = "Harvey", ubicacion = "Medical Clinic", thumbnailResId = R.drawable.harvey),
            ContactoEntity(name = "Leah", ubicacion = "Leah's Cottage", thumbnailResId = R.drawable.leah),
            ContactoEntity(name = "Maru", ubicacion = "24 Mountain Road", thumbnailResId = R.drawable.maru),
            ContactoEntity(name = "Penny", ubicacion = "Trailer", thumbnailResId = R.drawable.penny),
            ContactoEntity(name = "Sam", ubicacion = "1 Willow Lane", thumbnailResId = R.drawable.sam),
            ContactoEntity(name = "Sebastian", ubicacion = "24 Mountain Road", thumbnailResId = R.drawable.sebastian),
            ContactoEntity(name = "Shane", ubicacion = "Marnie's Ranch", thumbnailResId = R.drawable.shane)
        )
    }
}
