package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarContacto(contacto: ContactoEntity)
    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)
    @Delete
    suspend fun eliminarContacto(contacto: ContactoEntity)

    @Query("SELECT * FROM contactos ORDER BY nombre ASC")
    fun obtenerTodosContactos(): Flow<List<ContactoEntity>>

    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun obtenerContactoPorId(id: Int): ContactoEntity?
}