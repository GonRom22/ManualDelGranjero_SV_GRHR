package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactoRepository @Inject constructor(
    private val contactoDao: ContactoDao
){
    fun obtenerTodosContactos(): Flow<List<ContactoEntity>> = contactoDao.obtenerTodosContactos()

    suspend fun insertarContacto(contacto: ContactoEntity) = contactoDao.insertarContacto(contacto)

    suspend fun actualizarContacto(contacto: ContactoEntity) = contactoDao.actualizarContacto(contacto)

    suspend fun eliminarContacto(contacto: ContactoEntity) = contactoDao.eliminarContacto(contacto)

    suspend fun obtenerContactoPorId(id:Int): ContactoEntity? = contactoDao.obtenerContactoPorId(id)
}