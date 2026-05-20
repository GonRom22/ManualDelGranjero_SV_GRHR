package com.example.agendacontactosgrhr.data.repository

import android.util.Log
import com.example.agendacontactosgrhr.data.local.dao.EdificioDao
import com.example.agendacontactosgrhr.data.local.entity.EdificioEntity
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EdificioRepository @Inject constructor(
    private val edificioDao: EdificioDao,
    private val apiService: ApiService
) {
    fun obtenerTodosLosEdificios(): Flow<List<EdificioEntity>> = 
        edificioDao.obtenerTodosLosEdificios()

    suspend fun sincronizarEdificios(): Result<Unit> {
        return try {
            val response = apiService.getEdificios()
            if (response.datos.isNotEmpty()) {
                val entities = response.datos.map { dto ->
                    // Agregamos los materiales en un string descriptivo
                    val materiales = mutableListOf<String>()
                    if (dto.cantMadera > 0) materiales.add("${dto.cantMadera} Madera")
                    if (dto.cantPiedra > 0) materiales.add("${dto.cantPiedra} Piedra")
                    if (dto.cantMaderaNoble > 0) materiales.add("${dto.cantMaderaNoble} Madera Noble")
                    if (dto.cantFibra > 0) materiales.add("${dto.cantFibra} Fibra")
                    if (dto.cantArcilla > 0) materiales.add("${dto.cantArcilla} Arcilla")
                    if (dto.otrosMateriales != null) materiales.add(dto.otrosMateriales)

                    EdificioEntity(
                        id = dto.id,
                        nombre = dto.nombre,
                        tiempoConstruccion = dto.tiempoConstruccion,
                        costeOro = dto.costeOro,
                        materialesNecesarios = materiales.joinToString(", ")
                    )
                }
                edificioDao.insertarEdificios(entities)
                Result.success(Unit)
            } else {
                Result.failure(Exception("La API no devolvió edificios"))
            }
        } catch (e: Exception) {
            Log.e("EdificioRepository", "Error al sincronizar edificios: ${e.message}")
            Result.failure(e)
        }
    }
}
