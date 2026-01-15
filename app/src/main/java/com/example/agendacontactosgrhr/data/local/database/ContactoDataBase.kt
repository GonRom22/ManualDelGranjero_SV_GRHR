package com.example.agendacontactosgrhr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity

@Database(entities = [ContactoEntity::class], version = 1, exportSchema = false)
abstract class ContactoDataBase: RoomDatabase(){
    abstract fun contactoDao(): ContactoDao
}