package com.example.agendacontactosgrhr.data.repository

import android.util.Log
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio encargado de gestionar el acceso a los datos de Contactos.
 *
 * Actúa como una capa intermedia entre el DAO y el ViewModel.
 * Permite centralizar la lógica de acceso a datos y facilita
 * futuras modificaciones (por ejemplo, añadir una API remota).
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

    // 🔥 NUEVO: traer TODOS los NPCs desde API
    suspend fun importarTodosDesdeApi(): List<ContactoEntity> {
        return dataSource.getCharacters()
    }
}