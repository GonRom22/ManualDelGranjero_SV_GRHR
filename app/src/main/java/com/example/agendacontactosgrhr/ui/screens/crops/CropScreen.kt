package com.example.agendacontactosgrhr.ui.screens.crops

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.CropsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CropScreen(navController: NavHostController) {
    val viewModel: CropsViewModel = hiltViewModel()
    val crops by viewModel.crops.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Escuchar eventos de la UI (mensajes de éxito/error)
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    PantallaBase(
        titulo = "Cultivos de Stardew Valley",
        navController = navController,
        acciones = {
            IconButton(onClick = { viewModel.syncCrops() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Sincronizar con API")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(crops) { crop ->
                        CropItem(crop = crop, onClick = {
                            navController.navigate("${Screens.DetailCrop.route}/${crop.id}")
                        }, onFavoriteClick = {
                            viewModel.toggleFavorite(crop.id)
                        })
                    }
                }
            }
            
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun CropItem(crop: CropEntity, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageRes = drawableMap[crop.nombre.lowercase()] ?: com.example.agendacontactosgrhr.R.drawable.ic_launcher_background

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Imagen de ${crop.nombre}",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = crop.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = crop.temporada,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (crop.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (crop.isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                        tint = if (crop.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                CropStat(label = "Semilla", value = "${crop.precioSemilla}g", modifier = Modifier.weight(1f))
                CropStat(label = "Venta", value = "${crop.precioVenta}g", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                CropStat(label = "Crecimiento", value = "${crop.tiempoCrecimiento} días", modifier = Modifier.weight(1f))
                if (crop.tiempoRegreso > 0) {
                    CropStat(label = "Regreso", value = "${crop.tiempoRegreso} días", modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun CropStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
