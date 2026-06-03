package com.example.agendacontactosgrhr.ui.screens.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.agendacontactosgrhr.R
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.FavViewModel

@Composable
fun FavScreen(navController: NavHostController) {
    val viewModel: FavViewModel = hiltViewModel()
    val (favoriteNpcs, favoriteCrops) = viewModel.allFavorites.collectAsState().value

    PantallaBase(
        titulo = "Favoritos",
        navController = navController
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (favoriteNpcs.isNotEmpty()) {
                item {
                    Text(
                        text = "NPCs Favoritos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(favoriteNpcs) { npc ->
                    NpcItem(npc = npc, onClick = {
                        navController.navigate("${Screens.DetailNpc.route}/${npc.id}")
                    }, onFavoriteClick = {
                        viewModel.toggleFavoriteNpc(npc.id)
                    })
                }
            }

            if (favoriteCrops.isNotEmpty()) {
                item {
                    Text(
                        text = "Cultivos Favoritos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(favoriteCrops) { crop ->
                    CropItem(crop = crop, onClick = {
                        navController.navigate("${Screens.DetailCrop.route}/${crop.id}")
                    }, onFavoriteClick = {
                        viewModel.toggleFavoriteCrop(crop.id)
                    })
                }
            }

            if (favoriteNpcs.isEmpty() && favoriteCrops.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tienes favoritos aún. Agrega algunos desde las listas de NPCs o Cultivos.",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NpcItem(npc: ContactoEntity, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    val drawableMap = mapOf(
        "abigail" to R.drawable.abigail,
        "alex" to R.drawable.alex,
        "caroline" to R.drawable.caroline,
        "clint" to R.drawable.clint,
        "demetrius" to R.drawable.demetrius,
        "dwarf" to R.drawable.dwarf,
        "elliott" to R.drawable.elliott,
        "emily" to R.drawable.emily,
        "evelyn" to R.drawable.evelyn,
        "george" to R.drawable.george,
        "gonzalo" to R.drawable.gonzalo,
        "gus" to R.drawable.gus,
        "haley" to R.drawable.haley,
        "harvey" to R.drawable.harvey,
        "jas" to R.drawable.jas,
        "jodi" to R.drawable.jodi,
        "kent" to R.drawable.kent,
        "krobus" to R.drawable.krobus,
        "leah" to R.drawable.leah,
        "leo" to R.drawable.leo,
        "lewis" to R.drawable.lewis,
        "linus" to R.drawable.linus,
        "marnie" to R.drawable.marnie,
        "maru" to R.drawable.maru,
        "pam" to R.drawable.pam,
        "penny" to R.drawable.penny,
        "pierre" to R.drawable.pierre,
        "robin" to R.drawable.robin,
        "sam" to R.drawable.sam,
        "sandy" to R.drawable.sandy,
        "sebastian" to R.drawable.sebastian,
        "shane" to R.drawable.shane,
        "vincent" to R.drawable.vincent,
        "willy" to R.drawable.willy,
        "wizard" to R.drawable.wizard
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val placeholderRes = npc.thumbnailResId?.takeIf { it != 0 } 
                ?: R.drawable.ic_launcher_background

            val nameFromUrl = when {
                npc.thumbnail.contains("imagenes/") -> npc.thumbnail.substringAfter("imagenes/").substringBefore(".png")
                npc.thumbnail.contains("personajes/") -> npc.thumbnail.substringAfter("personajes/").substringBefore(".png")
                else -> null
            }?.lowercase()

            val finalLocalRes = nameFromUrl?.let { drawableMap[it] } ?: placeholderRes

            AsyncImage(
                model = npc.thumbnail.takeIf { it.isNotEmpty() } ?: finalLocalRes,
                contentDescription = "Portrait of ${npc.name}",
                placeholder = painterResource(id = finalLocalRes),
                error = painterResource(id = finalLocalRes),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = npc.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = npc.ubicacion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun CropItem(crop: CropEntity, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    val drawableMap = mapOf(
        "ajo" to R.drawable.ajo,
        "patata" to R.drawable.papa,
        "papa" to R.drawable.papa,
        "tulipán" to R.drawable.tulipan,
        "col rizada" to R.drawable.col_rizada,
        "jazz azul" to R.drawable.jazz_azul,
        "fresa" to R.drawable.fresa,
        "judía verde" to R.drawable.ejote,
        "coliflor" to R.drawable.coliflor,
        "ruibarbo" to R.drawable.ruibarbo,
        "arroz sin moler" to R.drawable.arroz_sin_moler,
        "trigo" to R.drawable.trigo,
        "chile" to R.drawable.pimiento_picante,
        "rábano" to R.drawable.rabano,
        "amapola" to R.drawable.amapola,
        "lentejuela de verano" to R.drawable.lentejuela_de_verano,
        "lúpulo" to R.drawable.lupulo,
        "tomate" to R.drawable.tomate,
        "melón" to R.drawable.melon,
        "arándano" to R.drawable.arandano,
        "carambola" to R.drawable.fruta_estrella,
        "maíz" to R.drawable.maiz,
        "lombarda" to R.drawable.col_roja,
        "girasol" to R.drawable.girasol,
        "col china" to R.drawable.bok_choy,
        "alcachofa" to R.drawable.alcachofa,
        "ñame" to R.drawable.yam,
        "uva" to R.drawable.uva,
        "rosa hada" to R.drawable.hada_rosa,
        "chirivía" to R.drawable.chirivia,
        "berenjena" to R.drawable.berenjena,
        "remolacha" to R.drawable.remolacha,
        "arándano rojo" to R.drawable.arandano_rojo,
        "amaranto" to R.drawable.amaranto,
        "calabaza" to R.drawable.calabaza,
        "grano de café" to R.drawable.grano_cafe,
        "fruta milenaria" to R.drawable.fruta_antigua,
        "baya de gema dulce" to R.drawable.baya_gema_dulce,
        "fruta de cactus" to R.drawable.fruta_de_cactus,
        "flor de té" to R.drawable.hojas_te
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageRes = drawableMap[crop.nombre.lowercase()] ?: R.drawable.ic_launcher_background
                
                AsyncImage(
                    model = imageRes,
                    contentDescription = "Image of ${crop.nombre}",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
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
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Quitar de favoritos",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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
