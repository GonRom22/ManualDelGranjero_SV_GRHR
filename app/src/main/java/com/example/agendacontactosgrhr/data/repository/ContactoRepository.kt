package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService

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
    private val contactoDao: ContactoDao,
    //Vamos a inyectar la API como fuente de datos
    private val dataSource: ApiService
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

    /**Función para obtener contacto desde la API
     * Es asíncrona/hace peticiones a la red y por eso es suspended*/
    suspend fun getNewContact(): ContactoEntity{
        val resultado = dataSource.getContact().results.first()

        val contactoEntity = ContactoEntity(
            //Aquí los nombres varían según se llamen en la propia API
            title = resultado.name.title,
            nombre = resultado.name.name,
            lastName = resultado.name.last,
            phone = resultado.contact.phone,
            email = resultado.contact.email,
            city = resultado.location.city,
            country = resultado.location.country,
            thumbnail = resultado.picture.thumbnail
        )
        return contactoEntity
    }
}