package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agendacontactosgrhr.data.local.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMateriales(materiales: List<MaterialEntity>)

    @Query("SELECT * FROM materiales ORDER BY nombre ASC")
    fun obtenerTodosLosMateriales(): Flow<List<MaterialEntity>>

    @Query("SELECT COUNT(*) FROM materiales")
    suspend fun contarMateriales(): Int
}
