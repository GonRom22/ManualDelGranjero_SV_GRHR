package com.example.agendacontactosgrhr.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

/**
 * He añadido este Dao para configurar las 3 querys:
 */
@Dao
interface NpcDao {

    //Cumpleaños
    @Query("SELECT * FROM ContactoEntity WHERE estacionCumple = :estacion AND cumpleanos = :dia LIMIT 1")
    fun obtenerCumpleanos(estacion: String, dia: Int): Flow<ContactoEntity?>

    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)

    //Filtro de candidatos para matrimonio
    @Query("SELECT * FROM ContactoEntity WHERE esSoltero = 1 AND nivelAmistad >= 250 LIMIT 1 ") //Hay que verificar si en la base de datos se guarda como booleano o como 0 o 1 el valor esSoltero

    fun obtenerCandidatosMatrimonio(): Flow<List<ContactoEntity>>

    //Gestión de regalos: Reseteo del contador semanal de regalos:
    @Query("UPDATE ContactoEntity SET regalosDadosEstaSemana = 0 WHERE id = :contactoId")
    suspend fun resetearRegalos()

    //Gestión de regalos: Busqueda parcial por regalos
    @Query("SELECT * FROM ContactoEntity WHERE regalosAmados LIKE '%' || :item || '%'") //En esta consulta el like busca resultados parciales y los simbolos de '%' son comodines.
    fun buscarNpcPorRegaloFavorito(item: String): Flow<List<ContactoEntity>>


}