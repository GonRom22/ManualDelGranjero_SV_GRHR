package com.example.agendacontactosgrhr.ui.screens.npcs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
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
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

/**
 * Esta pantalla recupera y muestra la info detallada de los contactos
 * utilizando un collectAsState y basándose en la ID del contacto.
 */
@Composable
fun DetailNpcScreen(
    navController: NavHostController,
    npcId: Int
) {
    val viewModel: ContactosViewModel = hiltViewModel()
    val npc = viewModel.contactos.collectAsState().value
        .firstOrNull { it.id == npcId }

    PantallaBase(
        titulo = npc?.name ?: "Detalle NPC",
        navController = navController,
        acciones = {
            if (npc != null) {
                IconButton(onClick = { viewModel.toggleFavorite(npc.id) }) {
                    Icon(
                        imageVector = if (npc.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (npc.isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                        tint = if (npc.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    ) { padding ->

        if (npc == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
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

                // NPC Image - Centered and first thing shown
                Image(
                    painter = painterResource(id = fallbackRes),
                    contentDescription = npc.name,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                // NPC Info
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoRow(label = "Nombre", value = npc.name)
                        InfoRow(label = "Ubicación", value = npc.ubicacion)
                        
                        if (npc.cumpleanos != 0) {
                            InfoRow(label = "Cumpleaños", value = "${npc.cumpleanos} de ${npc.estacionCumpleanos}")
                        }
                        
                        if (npc.regalosAmados.isNotEmpty()) {
                            InfoRow(label = "Regalos amados", value = npc.regalosAmados)
                        }
                        
                        if (npc.regalosOdiados.isNotEmpty()) {
                            InfoRow(label = "Regalos odiados", value = npc.regalosOdiados)
                        }
                        
                        InfoRow(label = "Nivel de Amistad", value = "${npc.nivelAmistad} puntos")
                        
                        InfoRow(label = "Estación", value = npc.estacion)
                        InfoRow(label = "Soltero", value = if (npc.esSoltero) "Sí" else "No")
                        InfoRow(label = "Hablado hoy", value = if (npc.habladoHoy) "Sí" else "No")
                        InfoRow(label = "Regalo recibido hoy", value = if (npc.regaloRecibidoHoy) "Sí" else "No")
                        InfoRow(label = "Posición", value = npc.posicion)
                        InfoRow(label = "Favorito", value = if (npc.isFavorite) "Sí" else "No")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
