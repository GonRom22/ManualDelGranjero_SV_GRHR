package com.example.agendacontactosgrhr.data.repository

import android.util.Log
import com.example.agendacontactosgrhr.data.local.dao.MaterialDao
import com.example.agendacontactosgrhr.data.local.entity.MaterialEntity
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaterialRepository @Inject constructor(
    private val materialDao: MaterialDao,
    private val apiService: ApiService
) {
    fun obtenerTodosLosMateriales(): Flow<List<MaterialEntity>> = 
        materialDao.obtenerTodosLosMateriales()

    suspend fun sincronizarMateriales(): Result<Unit> {
        return try {
            val response = apiService.getMateriales()
            if (response.datos.isNotEmpty()) {
                val entities = response.datos.map { dto ->
                    MaterialEntity(
                        id = dto.id,
                        nombre = dto.nombre,
                        fuente = dto.fuente ?: "Desconocida",
                        precioVenta = dto.precioVenta,
                        descripcion = dto.descripcion ?: ""
                    )
                }
                materialDao.insertarMateriales(entities)
                Result.success(Unit)
            } else {
                Result.failure(Exception("La API no devolvió materiales"))
            }
        } catch (e: Exception) {
            Log.e("MaterialRepository", "Error al sincronizar materiales: ${e.message}")
            Result.failure(e)
        }
    }
}
