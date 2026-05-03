package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.local.dao.CropDao
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CropRepository @Inject constructor(
    private val cropDao: CropDao
) {
    fun obtenerTodosLosCultivos(): Flow<List<CropEntity>> = cropDao.obtenerTodosLosCultivos()

    suspend fun insertarCultivos(cultivos: List<CropEntity>) = cropDao.insertarCultivos(cultivos)

    suspend fun contarCultivos(): Int = cropDao.contarCultivos()

    fun getMockCrops(): List<CropEntity> {
        return listOf(
            CropEntity(nombre = "Chirivía", precioSemilla = 20, precioVenta = 35, tiempoCrecimiento = 4, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Ajo", precioSemilla = 40, precioVenta = 60, tiempoCrecimiento = 4, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Patata", precioSemilla = 50, precioVenta = 80, tiempoCrecimiento = 6, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Tulipán", precioSemilla = 20, precioVenta = 30, tiempoCrecimiento = 6, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Col rizada", precioSemilla = 70, precioVenta = 110, tiempoCrecimiento = 6, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Jazz azul", precioSemilla = 30, precioVenta = 50, tiempoCrecimiento = 7, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Fresa", precioSemilla = 100, precioVenta = 120, tiempoCrecimiento = 8, tiempoRegreso = 4, temporada = "Primavera"),
            CropEntity(nombre = "Judía verde", precioSemilla = 60, precioVenta = 40, tiempoCrecimiento = 10, tiempoRegreso = 3, temporada = "Primavera"),
            CropEntity(nombre = "Coliflor", precioSemilla = 80, precioVenta = 175, tiempoCrecimiento = 12, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Ruibarbo", precioSemilla = 100, precioVenta = 220, tiempoCrecimiento = 13, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Arroz sin moler", precioSemilla = 40, precioVenta = 30, tiempoCrecimiento = 8, tiempoRegreso = 0, temporada = "Primavera"),
            CropEntity(nombre = "Trigo", precioSemilla = 10, precioVenta = 25, tiempoCrecimiento = 4, tiempoRegreso = 0, temporada = "Varias"),
            CropEntity(nombre = "Chile", precioSemilla = 40, precioVenta = 40, tiempoCrecimiento = 5, tiempoRegreso = 3, temporada = "Verano"),
            CropEntity(nombre = "Rábano", precioSemilla = 40, precioVenta = 90, tiempoCrecimiento = 6, tiempoRegreso = 0, temporada = "Verano"),
            CropEntity(nombre = "Amapola", precioSemilla = 100, precioVenta = 140, tiempoCrecimiento = 7, tiempoRegreso = 0, temporada = "Verano"),
            CropEntity(nombre = "Lentejuela de verano", precioSemilla = 50, precioVenta = 90, tiempoCrecimiento = 8, tiempoRegreso = 0, temporada = "Verano"),
            CropEntity(nombre = "Lúpulo", precioSemilla = 60, precioVenta = 25, tiempoCrecimiento = 11, tiempoRegreso = 1, temporada = "Verano"),
            CropEntity(nombre = "Tomate", precioSemilla = 50, precioVenta = 60, tiempoCrecimiento = 11, tiempoRegreso = 4, temporada = "Verano"),
            CropEntity(nombre = "Melón", precioSemilla = 80, precioVenta = 250, tiempoCrecimiento = 12, tiempoRegreso = 0, temporada = "Verano"),
            CropEntity(nombre = "Arándano", precioSemilla = 80, precioVenta = 50, tiempoCrecimiento = 13, tiempoRegreso = 4, temporada = "Verano"),
            CropEntity(nombre = "Carambola", precioSemilla = 400, precioVenta = 750, tiempoCrecimiento = 13, tiempoRegreso = 0, temporada = "Verano"),
            CropEntity(nombre = "Maíz", precioSemilla = 150, precioVenta = 50, tiempoCrecimiento = 14, tiempoRegreso = 4, temporada = "Varias"),
            CropEntity(nombre = "Lombarda", precioSemilla = 100, precioVenta = 260, tiempoCrecimiento = 9, tiempoRegreso = 0, temporada = "Verano"),
            CropEntity(nombre = "Girasol", precioSemilla = 200, precioVenta = 80, tiempoCrecimiento = 8, tiempoRegreso = 0, temporada = "Varias"),
            CropEntity(nombre = "Col china", precioSemilla = 50, precioVenta = 80, tiempoCrecimiento = 4, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Berenjena", precioSemilla = 20, precioVenta = 60, tiempoCrecimiento = 5, tiempoRegreso = 5, temporada = "Otoño"),
            CropEntity(nombre = "Remolacha", precioSemilla = 20, precioVenta = 100, tiempoCrecimiento = 6, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Arándano rojo", precioSemilla = 240, precioVenta = 75, tiempoCrecimiento = 7, tiempoRegreso = 5, temporada = "Otoño"),
            CropEntity(nombre = "Amaranto", precioSemilla = 70, precioVenta = 150, tiempoCrecimiento = 7, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Alcachofa", precioSemilla = 30, precioVenta = 160, tiempoCrecimiento = 8, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Ñame", precioSemilla = 60, precioVenta = 160, tiempoCrecimiento = 10, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Uva", precioSemilla = 60, precioVenta = 80, tiempoCrecimiento = 10, tiempoRegreso = 3, temporada = "Otoño"),
            CropEntity(nombre = "Rosa hada", precioSemilla = 200, precioVenta = 290, tiempoCrecimiento = 12, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Calabaza", precioSemilla = 100, precioVenta = 320, tiempoCrecimiento = 13, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Grano de café", precioSemilla = 2500, precioVenta = 15, tiempoCrecimiento = 10, tiempoRegreso = 2, temporada = "Varias"),
            CropEntity(nombre = "Fruta milenaria", precioSemilla = 0, precioVenta = 550, tiempoCrecimiento = 28, tiempoRegreso = 7, temporada = "Varias"),
            CropEntity(nombre = "Baya de gema dulce", precioSemilla = 1000, precioVenta = 3000, tiempoCrecimiento = 24, tiempoRegreso = 0, temporada = "Otoño"),
            CropEntity(nombre = "Fruta de cactus", precioSemilla = 0, precioVenta = 75, tiempoCrecimiento = 12, tiempoRegreso = 3, temporada = "Invernadero")
        )
    }
}
