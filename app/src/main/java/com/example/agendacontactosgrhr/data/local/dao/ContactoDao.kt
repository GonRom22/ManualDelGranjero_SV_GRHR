package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactoDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarContacto(contacto: ContactoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarListaContactos(contactos: List<ContactoEntity>)

    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)

    @Delete
    suspend fun eliminarContacto(contacto: ContactoEntity)

    @Query("SELECT * FROM contactos ORDER BY name ASC")
    fun obtenerTodosContactos(): Flow<List<ContactoEntity>>

    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun obtenerContactoPorId(id: Int): ContactoEntity?

    @Query("SELECT * FROM contactos WHERE isFavorite = 1 ORDER BY name ASC")
    fun obtenerFavoritos(): Flow<List<ContactoEntity>>

    @Query("UPDATE contactos SET isFavorite = :isFav WHERE id = :id")
    suspend fun actualizarFavorito(id: Int, isFav: Boolean)

    @Query("SELECT * FROM contactos WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun buscarPorNombre(query: String): Flow<List<ContactoEntity>>

    @Query("SELECT * FROM contactos WHERE estacionCumpleanos = :estacion AND cumpleanos = :dia LIMIT 1")
    fun obtenerCumpleanos(estacion: String, dia: Int): Flow<ContactoEntity?>

    @Query("SELECT * FROM contactos WHERE esSoltero = 1 AND nivelAmistad >= 250")
    fun obtenerCandidatosMatrimonio(): Flow<List<ContactoEntity>>

    @Query("UPDATE contactos SET regaloRecibidoHoy = false WHERE id = :contactoId")
    suspend fun resetearRegalos(contactoId: Int)

    @Query("SELECT * FROM contactos WHERE regalosAmados LIKE '%' || :item || '%'")
    fun buscarNpcPorRegaloFavorito(item: String): Flow<List<ContactoEntity>>
}
