package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para la entidad Contacto.
 *
 * Esta interfaz define todas las operaciones de acceso a la base de datos
 * relacionadas con la tabla contactos.
 *
 * Room se encarga de generar automáticamente la implementación de estas
 * funciones.
 */

@Dao
interface ContactoDao{
    /**
     * Inserta un contacto en la base de datos.
     * Si ya existe un contacto con el mismo id, se reemplaza.
     *
     * Se usa una función suspendida porque el acceso a la base de datos
     * es lento y costoso y debe ejecutarse dentro de una corrutina.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarContacto(contacto: ContactoEntity)

    /**Actualiza un contacto existente en la base de datos.
     * La actualización se realiza utilizando la clave primaria (id).*/
    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)

    /**Elimina un contacto específico de la base de datos.
     * Se identifica mediante su clave primaria.*/
    @Delete
    suspend fun eliminarContacto(contacto: ContactoEntity)

    /**Obtiene todos los contactos almacenados en la base de datos,
     * ordenados alfabéticamente por el nombre.
     * Devuelve un Flow para poder observar los cambios en tiempo real.
     * Cada vez que la tabla contactos se modifica, se emite una nueva lista.*/
    @Query("SELECT * FROM contactos ORDER BY nombre ASC")
    fun obtenerTodosContactos(): Flow<List<ContactoEntity>>

    /**Obtiene un contacto específico según su id.
     * Devuelve null si no existe ningún contacto con ese id.*/
    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun obtenerContactoPorId(id: Int): ContactoEntity?
}