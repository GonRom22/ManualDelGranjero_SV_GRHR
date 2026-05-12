package com.example.agendacontactosgrhr.ui.screens.searcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
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
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel
import com.example.agendacontactosgrhr.viewmodel.CropsViewModel

sealed class SearchResult {
    data class NpcResult(val npc: com.example.agendacontactosgrhr.data.local.entity.ContactoEntity) : SearchResult()
    data class CropResult(val crop: com.example.agendacontactosgrhr.data.local.entity.CropEntity) : SearchResult()
}

@Composable
fun SearcherScreen(navController: NavHostController) {
    val contactosViewModel: ContactosViewModel = hiltViewModel()
    val cropsViewModel: CropsViewModel = hiltViewModel()
    val npcs by contactosViewModel.contactos.collectAsState()
    val crops by cropsViewModel.crops.collectAsState()
    var textoBusqueda by remember { mutableStateOf("") }

    val resultados: List<SearchResult> = if (textoBusqueda.isEmpty()) {
        emptyList()
    } else {
        val npcResults = npcs.filter {
            it.name.contains(textoBusqueda, ignoreCase = true) ||
            it.ubicacion.contains(textoBusqueda, ignoreCase = true)
        }.map { SearchResult.NpcResult(it) }

        val cropResults = crops.filter {
            it.nombre.contains(textoBusqueda, ignoreCase = true) ||
            it.temporada.contains(textoBusqueda, ignoreCase = true)
        }.map { SearchResult.CropResult(it) }

        npcResults + cropResults
    }

    PantallaBase(
        titulo = "Biblioteca de Pueblo Pelícano",
        navController = navController
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Introduce lo que buscas...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (textoBusqueda.isNotEmpty() && resultados.isEmpty()) {
                Text(
                    text = "No se encontraron personajes o cultivos para \"$textoBusqueda\"",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Results List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(resultados) { result ->
                    when (result) {
                        is SearchResult.NpcResult -> {
                            val npc = result.npc
                            val drawableMap = mapOf(
                                "abigail" to com.example.agendacontactosgrhr.R.drawable.abigail,
                                "alex" to com.example.agendacontactosgrhr.R.drawable.alex,
                                "caroline" to com.example.agendacontactosgrhr.R.drawable.caroline,
                                "clint" to com.example.agendacontactosgrhr.R.drawable.clint,
                                "demetrius" to com.example.agendacontactosgrhr.R.drawable.demetrius,
                                "dwarf" to com.example.agendacontactosgrhr.R.drawable.dwarf,
                                "elliott" to com.example.agendacontactosgrhr.R.drawable.elliott,
                                "emily" to com.example.agendacontactosgrhr.R.drawable.emily,
                                "evelyn" to com.example.agendacontactosgrhr.R.drawable.evelyn,
                                "george" to com.example.agendacontactosgrhr.R.drawable.george,
                                "gonzalo" to com.example.agendacontactosgrhr.R.drawable.gonzalo,
                                "gus" to com.example.agendacontactosgrhr.R.drawable.gus,
                                "haley" to com.example.agendacontactosgrhr.R.drawable.haley,
                                "harvey" to com.example.agendacontactosgrhr.R.drawable.harvey,
                                "jas" to com.example.agendacontactosgrhr.R.drawable.jas,
                                "jodi" to com.example.agendacontactosgrhr.R.drawable.jodi,
                                "kent" to com.example.agendacontactosgrhr.R.drawable.kent,
                                "krobus" to com.example.agendacontactosgrhr.R.drawable.krobus,
                                "leah" to com.example.agendacontactosgrhr.R.drawable.leah,
                                "leo" to com.example.agendacontactosgrhr.R.drawable.leo,
                                "lewis" to com.example.agendacontactosgrhr.R.drawable.lewis,
                                "linus" to com.example.agendacontactosgrhr.R.drawable.linus,
                                "marnie" to com.example.agendacontactosgrhr.R.drawable.marnie,
                                "maru" to com.example.agendacontactosgrhr.R.drawable.maru,
                                "pam" to com.example.agendacontactosgrhr.R.drawable.pam,
                                "penny" to com.example.agendacontactosgrhr.R.drawable.penny,
                                "pierre" to com.example.agendacontactosgrhr.R.drawable.pierre,
                                "robin" to com.example.agendacontactosgrhr.R.drawable.robin,
                                "sam" to com.example.agendacontactosgrhr.R.drawable.sam,
                                "sandy" to com.example.agendacontactosgrhr.R.drawable.sandy,
                                "sebastian" to com.example.agendacontactosgrhr.R.drawable.sebastian,
                                "shane" to com.example.agendacontactosgrhr.R.drawable.shane,
                                "vincent" to com.example.agendacontactosgrhr.R.drawable.vincent,
                                "willy" to com.example.agendacontactosgrhr.R.drawable.willy,
                                "wizard" to com.example.agendacontactosgrhr.R.drawable.wizard
                            )

                            val imageRes = npc.thumbnailResId?.takeIf { it != 0 } 
                                ?: com.example.agendacontactosgrhr.R.drawable.ic_launcher_background

                            val fallbackRes = if (npc.thumbnail.contains("imagenes/")) {
                                val name = npc.thumbnail.substringAfter("imagenes/").substringBefore(".png").lowercase()
                                drawableMap[name] ?: imageRes
                            } else {
                                imageRes
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("${Screens.DetailNpc.route}/${npc.id}")
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = fallbackRes),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column {
                                        Text(
                                            text = npc.name,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = npc.ubicacion,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                        is SearchResult.CropResult -> {
                            val crop = result.crop
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("${Screens.DetailCrop.route}/${crop.id}")
                                    },
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
                                        Text(
                                            text = crop.nombre,
                                            fontSize = 18.sp,
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

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = "Semilla: ${crop.precioSemilla}g",
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "Venta: ${crop.precioVenta}g",
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "Crecimiento: ${crop.tiempoCrecimiento} días",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
