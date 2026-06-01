package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agendacontactosgrhr.data.local.entity.EdificioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EdificioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEdificios(edificios: List<EdificioEntity>)

    @Query("SELECT * FROM edificios ORDER BY nombre ASC")
    fun obtenerTodosLosEdificios(): Flow<List<EdificioEntity>>

    @Query("SELECT * FROM edificios WHERE id = :id")
    suspend fun obtenerEdificioPorId(id: Int): EdificioEntity?

    @Query("SELECT COUNT(*) FROM edificios")
    suspend fun contarEdificios(): Int
}
