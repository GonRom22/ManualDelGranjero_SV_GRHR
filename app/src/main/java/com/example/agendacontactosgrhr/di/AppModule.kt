package com.example.agendacontactosgrhr.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.dao.CropDao
import com.example.agendacontactosgrhr.data.local.dao.MaterialDao
import com.example.agendacontactosgrhr.data.local.dao.EdificioDao
import com.example.agendacontactosgrhr.data.local.database.ContactoDataBase
import com.example.agendacontactosgrhr.data.network.NetworkMonitor
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): ContactoDataBase {
        return Room.databaseBuilder(
            context,
            ContactoDataBase::class.java,
            "contacto_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideContactoDao(db: ContactoDataBase): ContactoDao = db.contactoDao()

    @Provides
    @Singleton
    fun provideCropDao(db: ContactoDataBase): CropDao = db.cropDao()

    @Provides
    @Singleton
    fun provideMaterialDao(db: ContactoDataBase): MaterialDao = db.materialDao()

    @Provides
    @Singleton
    fun provideEdificioDao(db: ContactoDataBase): EdificioDao = db.edificioDao()

    @Provides
    @Singleton
    fun provideBaseURL(): String = "https://std-api-mdg.vercel.app/"

    @Provides
    @Singleton
    fun provideRetrofit(baseURL: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
