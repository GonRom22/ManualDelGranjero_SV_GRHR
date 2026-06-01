package com.example.agendacontactosgrhr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.dao.CropDao
import com.example.agendacontactosgrhr.data.local.dao.MaterialDao
import com.example.agendacontactosgrhr.data.local.dao.EdificioDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.data.local.entity.MaterialEntity
import com.example.agendacontactosgrhr.data.local.entity.EdificioEntity


/**
 * Clase que representa la base de datos de la aplicación.
 */
@Database(
    entities = [
        ContactoEntity::class, 
        CropEntity::class, 
        MaterialEntity::class, 
        EdificioEntity::class
    ], 
    version = 4, 
    exportSchema = false
)
abstract class ContactoDataBase: RoomDatabase(){
    abstract fun contactoDao(): ContactoDao
    abstract fun cropDao(): CropDao
    abstract fun materialDao(): MaterialDao
    abstract fun edificioDao(): EdificioDao
}