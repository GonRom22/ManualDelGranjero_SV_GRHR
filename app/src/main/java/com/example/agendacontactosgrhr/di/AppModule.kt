package com.example.agendacontactosgrhr.di

import android.app.Application
import androidx.room.Room
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.database.ContactoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt encargado de proporcionar las dependencias
 * relacionadas con la base de datos y el acceso a datos.
 *
 * @Module indica que esta clase define dependencias para Hilt.
 * @InstallIn(SingletonComponent::class) especifica que las
 * dependencias vivirán durante todo el ciclo de vida de la aplicación.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**Proporciona una instancia única de la base de datos Room.
     *- Usa Application como contexto para evitar fugas de memoria.
     *- La instancia se crea una sola vez gracias a @Singleton.*/
    @Provides
    @Singleton
    fun provideDatabase(context: Application): ContactoDataBase {
        return Room.databaseBuilder(
            context,
            ContactoDataBase::class.java,
            "contacto_database"
        ).build()
    }

    /**Proporciona el DAO de Contactos.
     * Room genera automáticamente la implementación del DAO.
     * Se obtiene a partir de la instancia de la base de datos.*/
    @Provides
    @Singleton
    fun provideDao(db: ContactoDataBase): ContactoDao = db.contactoDao()
}