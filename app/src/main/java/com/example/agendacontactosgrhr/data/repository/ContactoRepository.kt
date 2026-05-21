package com.example.agendacontactosgrhr.data.repository

import android.util.Log
import com.example.agendacontactosgrhr.data.local.dao.ContactoDao
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService
import com.example.agendacontactosgrhr.data.remote.model.CharacterDto
import com.example.agendacontactosgrhr.data.remote.model.StardewResponse
import com.example.agendacontactosgrhr.R
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio encargado de gestionar el acceso a los datos de Contactos/NPCs.
 */
@Singleton
class ContactoRepository @Inject constructor(
    private val contactoDao: ContactoDao,
    private val dataSource: ApiService
) {

    fun obtenerTodosContactos(): Flow<List<ContactoEntity>> =
        contactoDao.obtenerTodosContactos()

    suspend fun insertarContacto(contacto: ContactoEntity) =
        contactoDao.insertarContacto(contacto)

    suspend fun actualizarContacto(contacto: ContactoEntity) =
        contactoDao.actualizarContacto(contacto)

    suspend fun eliminarContacto(contacto: ContactoEntity) =
        contactoDao.eliminarContacto(contacto)

    suspend fun obtenerContactoPorId(id: Int): ContactoEntity? =
        contactoDao.obtenerContactoPorId(id)

    fun obtenerFavoritos(): Flow<List<ContactoEntity>> =
        contactoDao.obtenerFavoritos()

    suspend fun actualizarFavorito(id: Int, isFav: Boolean) =
        contactoDao.actualizarFavorito(id, isFav)

    suspend fun actualizarAmistad(id: Int, nuevoNivel: Int) =
        contactoDao.actualizarAmistad(id, nuevoNivel)

    suspend fun actualizarHabladoHoy(id: Int, hablado: Boolean) =
        contactoDao.actualizarHabladoHoy(id, hablado)

    suspend fun actualizarRegalosSemanales(id: Int, regalos: Int) =
        contactoDao.actualizarRegalosSemanales(id, regalos)

    /**
     * Sincroniza los NPCs desde la API y los guarda en la base de datos local.
     */
    suspend fun sincronizarNpcs(): Result<Unit> {
        return try {
            val response: StardewResponse<List<CharacterDto>> = dataSource.getPersonajes()
            if (response.datos.isNotEmpty()) {
                val ubicaciones = mapOf(
                    "Abigail" to "Pierre's General Store",
                    "Alex" to "1 River Road",
                    "Elliott" to "Elliott's Cabin",
                    "Emily" to "2 Willow Lane",
                    "Haley" to "2 Willow Lane",
                    "Harvey" to "Medical Clinic",
                    "Leah" to "Leah's Cottage",
                    "Maru" to "24 Mountain Road",
                    "Penny" to "Trailer",
                    "Sam" to "1 Willow Lane",
                    "Sebastian" to "24 Mountain Road",
                    "Shane" to "Marnie's Ranch",
                    "Caroline" to "Pierre's General Store",
                    "Clint" to "Blacksmith",
                    "Demetrius" to "24 Mountain Road",
                    "Dwarf" to "Mines",
                    "Evelyn" to "1 River Road",
                    "George" to "1 River Road",
                    "Gus" to "Stardrop Saloon",
                    "Jas" to "Marnie's Ranch",
                    "Jodi" to "1 Willow Lane",
                    "Kent" to "1 Willow Lane",
                    "Krobus" to "Sewers",
                    "Leo" to "Ginger Island",
                    "Lewis" to "Mayor's Manor",
                    "Linus" to "Tent",
                    "Marnie" to "Marnie's Ranch",
                    "Pam" to "Trailer",
                    "Pierre" to "Pierre's General Store",
                    "Robin" to "Carpenter's Shop",
                    "Sandy" to "Oasis",
                    "Vincent" to "1 Willow Lane",
                    "Willy" to "Fish Shop",
                    "Wizard" to "Wizard's Tower"
                )
                val entities = response.datos.map { dto ->
                    val fullImageUrl = if (dto.imagenUrl != null) "https://std-api-mdg.vercel.app/${dto.imagenUrl}" else ""
                    ContactoEntity(
                        name = dto.nombre,
                        thumbnail = fullImageUrl,
                        esSoltero = dto.esSoltero == 1,
                        estacionCumpleanos = dto.temporadaCumpleanos ?: "",
                        cumpleanos = dto.diaCumpleanos ?: 0,
                        regalosAmados = dto.regalosAmados ?: "",
                        regalosOdiados = dto.regalosOdiados ?: "",
                        ubicacion = ubicaciones[dto.nombre] ?: "",
                        posicion = ""
                    )
                }
                contactoDao.insertarListaContactos(entities)
                Result.success(Unit)
            } else {
                Result.failure(Exception("La API no devolvió personajes"))
            }
        } catch (e: Exception) {
            Log.e("ContactoRepository", "Error al sincronizar NPCs: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Mantenemos esta función por compatibilidad o para carga inicial si la API falla.
     */
    fun obtenerNPCsLocales(): List<ContactoEntity> {
        return listOf(
            ContactoEntity(name = "Abigail", ubicacion = "Pierre's General Store", thumbnailResId = R.drawable.abigail),
            ContactoEntity(name = "Alex", ubicacion = "1 River Road", thumbnailResId = R.drawable.alex),
            ContactoEntity(name = "Elliott", ubicacion = "Elliott's Cabin", thumbnailResId = R.drawable.elliott),
            ContactoEntity(name = "Emily", ubicacion = "2 Willow Lane", thumbnailResId = R.drawable.emily),
            ContactoEntity(name = "Gonzalo", ubicacion = "Desconocido", thumbnailResId = R.drawable.gonzalo),
            ContactoEntity(name = "Haley", ubicacion = "2 Willow Lane", thumbnailResId = R.drawable.haley),
            ContactoEntity(name = "Harvey", ubicacion = "Medical Clinic", thumbnailResId = R.drawable.harvey),
            ContactoEntity(name = "Leah", ubicacion = "Leah's Cottage", thumbnailResId = R.drawable.leah),
            ContactoEntity(name = "Maru", ubicacion = "24 Mountain Road", thumbnailResId = R.drawable.maru),
            ContactoEntity(name = "Penny", ubicacion = "Trailer", thumbnailResId = R.drawable.penny),
            ContactoEntity(name = "Sam", ubicacion = "1 Willow Lane", thumbnailResId = R.drawable.sam),
            ContactoEntity(name = "Sebastian", ubicacion = "24 Mountain Road", thumbnailResId = R.drawable.sebastian),
            ContactoEntity(name = "Shane", ubicacion = "Marnie's Ranch", thumbnailResId = R.drawable.shane)
        )
    }
}
