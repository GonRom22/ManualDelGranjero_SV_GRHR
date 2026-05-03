package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT * FROM cultivos")
    fun obtenerTodosLosCultivos(): Flow<List<CropEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCultivos(cultivos: List<CropEntity>)

    @Query("SELECT COUNT(*) FROM cultivos")
    suspend fun contarCultivos(): Int
}
