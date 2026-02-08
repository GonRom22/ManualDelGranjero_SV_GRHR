package com.example.agendacontactosgrhr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity


/**
 * Clase que representa la base de datos de la aplicación.
 *
 * @Database indica a Room:
 * - Las entidades que forman parte de la base de datos.
 * - La versión de la base de datos.
 * - Si se exporta o no el esquema (útil para migraciones).
 *
 * Esta clase debe ser abstracta y extender de RoomDatabase.
 */
@Database(entities = [ContactoEntity::class], version = 2, exportSchema = false)
abstract class ContactoDataBase: RoomDatabase(){
    /**Proporciona el DAO que permite acceder a las operaciones
     * de la tabla contactos.
     * Room se encarga de generar la implementación de este método.*/
    abstract fun contactoDao(): ContactoDao
}