package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT * FROM cultivos ORDER BY nombre ASC")
    fun obtenerTodosLosCultivos(): Flow<List<CropEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCultivos(cultivos: List<CropEntity>)

    @Query("SELECT COUNT(*) FROM cultivos")
    suspend fun contarCultivos(): Int

    @Query("SELECT * FROM cultivos WHERE id = :id")
    suspend fun obtenerCultivoPorId(id: Int): CropEntity?

    @Query("SELECT * FROM cultivos WHERE isFavorite = 1 ORDER BY nombre ASC")
    fun obtenerFavoritos(): Flow<List<CropEntity>>

    @Query("UPDATE cultivos SET isFavorite = :isFav WHERE id = :id")
    suspend fun actualizarFavorito(id: Int, isFav: Boolean)

    @Query("SELECT * FROM cultivos WHERE nombre LIKE '%' || :query || '%' ORDER BY nombre ASC")
    fun buscarPorNombre(query: String): Flow<List<CropEntity>>
    
    @Update
    suspend fun actualizarCultivo(cultivo: CropEntity)
}
