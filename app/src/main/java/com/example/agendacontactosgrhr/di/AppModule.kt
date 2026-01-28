package com.example.agendacontactosgrhr.di

import android.app.Application
import androidx.room.Room
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.database.ContactoDataBase
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
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
            "contacto_database"//Almacena la BBDD
        ).build()
    }

    /**Proporciona el DAO de Contactos.
     * Room genera automáticamente la implementación del DAO.
     * Se obtiene a partir de la instancia de la base de datos.*/
    @Provides
    @Singleton
    fun provideDao(db: ContactoDataBase): ContactoDao = db.contactoDao()
    /**Vamos a añadir el proveedor de la interfaz de la API.
     * Usamos Retrofit. */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
    //Aquí la URL de la API
    @Provides
    @Singleton
    fun provideBaseURL(): String = "https://randomuser.me/api/"
    //Aquí la función que hace peticiones a la API. Recibe la URL por inyección y convierte los JSON recibidos a objetos de Kotlin
    @Provides
    @Singleton
    fun provideRetrofit(baseURL: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

