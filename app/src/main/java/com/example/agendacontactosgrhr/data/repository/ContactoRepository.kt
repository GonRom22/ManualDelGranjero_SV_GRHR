package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repositorio encargado de gestionar el acceso a los datos de Contactos.
 *
 * Actúa como una capa intermedia entre el DAO y el ViewModel.
 * Permite centralizar la lógica de acceso a datos y facilita
 * futuras modificaciones (por ejemplo, añadir una API remota).
 */
class ContactoRepository @Inject constructor(
    private val contactoDao: ContactoDao
){
    /**Obtiene todos los contactos almacenados en la base de datos.
     * Devuelve un Flow para poder observar los cambios en tiempo real.*/
    fun obtenerTodosContactos(): Flow<List<ContactoEntity>> = contactoDao.obtenerTodosContactos()

    /** Inserta un nuevo contacto en la base de datos.*/
    suspend fun insertarContacto(contacto: ContactoEntity) = contactoDao.insertarContacto(contacto)

    /**Actualiza un contacto existente.*/
    suspend fun actualizarContacto(contacto: ContactoEntity) = contactoDao.actualizarContacto(contacto)

    /**Elimina un contacto de la base de datos.*/
    suspend fun eliminarContacto(contacto: ContactoEntity) = contactoDao.eliminarContacto(contacto)

    /**Obtiene un contacto específico a partir de su id.
     * Devuelve null si el contacto no existe.*/
    suspend fun obtenerContactoPorId(id:Int): ContactoEntity? = contactoDao.obtenerContactoPorId(id)
}