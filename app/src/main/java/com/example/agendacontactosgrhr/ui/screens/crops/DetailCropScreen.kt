package com.example.agendacontactosgrhr.ui.screens.crops

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.CropsViewModel

@Composable
fun DetailCropScreen(
    navController: NavHostController,
    cropId: Int
) {
    val viewModel: CropsViewModel = hiltViewModel()
    val crops by viewModel.crops.collectAsState()
    val crop = crops.firstOrNull { it.id == cropId }

    PantallaBase(
        titulo = crop?.nombre ?: "Detalle del Cultivo",
        navController = navController,
        acciones = {
            if (crop != null) {
                IconButton(onClick = { viewModel.toggleFavorite(crop.id) }) {
                    Icon(
                        imageVector = if (crop.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (crop.isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                        tint = if (crop.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    ) { padding ->
        if (crop == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val drawableMap = mapOf(
                    "ajo" to com.example.agendacontactosgrhr.R.drawable.ajo,
                    "patata" to com.example.agendacontactosgrhr.R.drawable.papa,
                    "tulipán" to com.example.agendacontactosgrhr.R.drawable.tulipan,
                    "col rizada" to com.example.agendacontactosgrhr.R.drawable.col_rizada,
                    "jazz azul" to com.example.agendacontactosgrhr.R.drawable.jazz_azul,
                    "fresa" to com.example.agendacontactosgrhr.R.drawable.fresa,
                    "judía verde" to com.example.agendacontactosgrhr.R.drawable.ejote,
                    "coliflor" to com.example.agendacontactosgrhr.R.drawable.coliflor,
                    "ruibarbo" to com.example.agendacontactosgrhr.R.drawable.ruibarbo,
                    "arroz sin moler" to com.example.agendacontactosgrhr.R.drawable.arroz_sin_moler,
                    "trigo" to com.example.agendacontactosgrhr.R.drawable.trigo,
                    "chile" to com.example.agendacontactosgrhr.R.drawable.pimiento_picante,
                    "rábano" to com.example.agendacontactosgrhr.R.drawable.rabano,
                    "amapola" to com.example.agendacontactosgrhr.R.drawable.amapola,
                    "lentejuela de verano" to com.example.agendacontactosgrhr.R.drawable.lentejuela_de_verano,
                    "lúpulo" to com.example.agendacontactosgrhr.R.drawable.lupulo,
                    "tomate" to com.example.agendacontactosgrhr.R.drawable.tomate,
                    "melón" to com.example.agendacontactosgrhr.R.drawable.melon,
                    "arándano" to com.example.agendacontactosgrhr.R.drawable.arandano,
                    "carambola" to com.example.agendacontactosgrhr.R.drawable.fruta_estrella,
                    "maíz" to com.example.agendacontactosgrhr.R.drawable.maiz,
                    "lombarda" to com.example.agendacontactosgrhr.R.drawable.col_roja,
                    "girasol" to com.example.agendacontactosgrhr.R.drawable.girasol,
                    "col china" to com.example.agendacontactosgrhr.R.drawable.bok_choy,
                    "alcachofa" to com.example.agendacontactosgrhr.R.drawable.alcachofa,
                    "ñame" to com.example.agendacontactosgrhr.R.drawable.yam,
                    "uva" to com.example.agendacontactosgrhr.R.drawable.uva,
                    "rosa hada" to com.example.agendacontactosgrhr.R.drawable.hada_rosa,
                    "chirivía" to com.example.agendacontactosgrhr.R.drawable.chirivia,
                    "berenjena" to com.example.agendacontactosgrhr.R.drawable.berenjena,
                    "remolacha" to com.example.agendacontactosgrhr.R.drawable.remolacha,
                    "arándano rojo" to com.example.agendacontactosgrhr.R.drawable.arandano_rojo,
                    "amaranto" to com.example.agendacontactosgrhr.R.drawable.amaranto,
                    "calabaza" to com.example.agendacontactosgrhr.R.drawable.calabaza,
                    "grano de café" to com.example.agendacontactosgrhr.R.drawable.grano_cafe,
                    "fruta milenaria" to com.example.agendacontactosgrhr.R.drawable.fruta_antigua,
                    "baya de gema dulce" to com.example.agendacontactosgrhr.R.drawable.baya_gema_dulce,
                    "fruta de cactus" to com.example.agendacontactosgrhr.R.drawable.fruta_de_cactus,
                    "flor de té" to com.example.agendacontactosgrhr.R.drawable.hojas_te
                )

                val imageRes = drawableMap[crop.nombre.lowercase()] ?: com.example.agendacontactosgrhr.R.drawable.ic_launcher_background

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Imagen de ${crop.nombre}",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = crop.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DetailRow(label = "Temporada", value = crop.temporada)
                        DetailRow(label = "Precio de Semilla", value = "${crop.precioSemilla} g")
                        DetailRow(label = "Precio de Venta", value = "${crop.precioVenta} g")
                        DetailRow(label = "Tiempo de Crecimiento", value = "${crop.tiempoCrecimiento} días")
                        
                        if (crop.tiempoRegreso > 0) {
                            DetailRow(label = "Tiempo de Regreso", value = "${crop.tiempoRegreso} días")
                        }

                        Divider()

                        // Cálculo de beneficio simple
                        val beneficio = crop.precioVenta - crop.precioSemilla
                        DetailRow(label = "Beneficio por cosecha", value = "${beneficio} g")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.SemiBold)
        Text(text = value)
    }
}
