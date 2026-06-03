package com.example.agendacontactosgrhr.ui.screens.npcs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.collectLatest
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

@Composable
fun NpcScreen(navController: NavHostController) {
    val viewModel: ContactosViewModel = hiltViewModel()
    val npcs by viewModel.contactos.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Escuchar eventos de la UI (mensajes de éxito/error)
    LaunchedEffect(Unit) {
        viewModel.eventoUI.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    PantallaBase(
        titulo = "Residentes de Pueblo Pelícano",
        navController = navController,
        acciones = {
            IconButton(onClick = { viewModel.importarTodosLosStardew() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Sincronizar con API")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(npcs) { npc ->
                        NpcItem(npc = npc, onClick = {
                            navController.navigate("${Screens.DetailNpc.route}/${npc.id}")
                        }, onFavoriteClick = {
                            viewModel.toggleFavorite(npc.id)
                        })
                    }
                }
                
                Surface(
                    tonalElevation = 8.dp,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = "Total NPCs: ${npcs.size}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
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
fun NpcItem(npc: com.example.agendacontactosgrhr.data.local.entity.ContactoEntity, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
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
            val imageRes = npc.thumbnailResId?.takeIf { it != 0 } 
                ?: com.example.agendacontactosgrhr.R.drawable.ic_launcher_background

            val fallbackRes = if (npc.thumbnail.contains("imagenes/")) {
                val name = npc.thumbnail.substringAfter("imagenes/").substringBefore(".png").lowercase()
                drawableMap[name] ?: imageRes
            } else {
                imageRes
            }

            Image(
                painter = painterResource(id = fallbackRes),
                contentDescription = "Portrait of ${npc.name}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = npc.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = npc.ubicacion,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (npc.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (npc.isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (npc.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
