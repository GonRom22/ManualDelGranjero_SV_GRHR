package com.example.agendacontactosgrhr.ui.screens.npcs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.agendacontactosgrhr.R
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

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
                        imageVector = if (npc.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (npc.isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                        tint = if (npc.isFavorite) Color.Red else Color.White
                    )
                }
            }
        }
    ) { padding ->

        if (npc == null) {
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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // --- IMAGEN ---
                val drawableMap = mapOf(
                    "abigail" to R.drawable.abigail, "alex" to R.drawable.alex, "caroline" to R.drawable.caroline,
                    "clint" to R.drawable.clint, "demetrius" to R.drawable.demetrius, "dwarf" to R.drawable.dwarf,
                    "elliott" to R.drawable.elliott, "emily" to R.drawable.emily, "evelyn" to R.drawable.evelyn,
                    "george" to R.drawable.george, "gonzalo" to R.drawable.gonzalo, "gus" to R.drawable.gus,
                    "haley" to R.drawable.haley, "harvey" to R.drawable.harvey, "jas" to R.drawable.jas,
                    "jodi" to R.drawable.jodi, "kent" to R.drawable.kent, "krobus" to R.drawable.krobus,
                    "leah" to R.drawable.leah, "leo" to R.drawable.leo, "lewis" to R.drawable.lewis,
                    "linus" to R.drawable.linus, "marnie" to R.drawable.marnie, "maru" to R.drawable.maru,
                    "pam" to R.drawable.pam, "penny" to R.drawable.penny, "pierre" to R.drawable.pierre,
                    "robin" to R.drawable.robin, "sam" to R.drawable.sam, "sandy" to R.drawable.sandy,
                    "sebastian" to R.drawable.sebastian, "shane" to R.drawable.shane, "vincent" to R.drawable.vincent,
                    "willy" to R.drawable.willy, "wizard" to R.drawable.wizard
                )

                val placeholderRes = npc.thumbnailResId?.takeIf { it != 0 } ?: R.drawable.ic_launcher_background
                val nameFromUrl = when {
                    npc.thumbnail.contains("imagenes/") -> npc.thumbnail.substringAfter("imagenes/").substringBefore(".png")
                    npc.thumbnail.contains("personajes/") -> npc.thumbnail.substringAfter("personajes/").substringBefore(".png")
                    else -> null
                }?.lowercase()
                val finalLocalRes = nameFromUrl?.let { drawableMap[it] } ?: placeholderRes

                AsyncImage(
                    model = npc.thumbnail.takeIf { it.isNotEmpty() } ?: finalLocalRes,
                    contentDescription = npc.name,
                    placeholder = painterResource(id = finalLocalRes),
                    error = painterResource(id = finalLocalRes),
                    modifier = Modifier.size(160.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = npc.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- INFORMACIÓN GENERAL (Moved up for visibility) ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "INFORMACIÓN GENERAL",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 18.sp
                            )
                        }
                        
                        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        
                        DetailInfoRow(label = "📍 Ubicación habitual", value = npc.ubicacion.ifBlank { "Desconocida" })
                        
                        val cumpleStr = if (npc.cumpleanos != 0) "${npc.cumpleanos} de ${npc.estacionCumpleanos}" else "Desconocido"
                        DetailInfoRow(label = "🎂 Cumpleaños", value = cumpleStr)
                        
                        DetailInfoRow(label = "💍 Estado civil", value = if (npc.esSoltero) "Soltero/a" else "Casado/a / Desconocido")
                        
                        DetailInfoRow(label = "💖 Regalos amados", value = npc.regalosAmados.ifBlank { "Sincroniza datos en el Home" })
                        DetailInfoRow(label = "💢 Regalos odiados", value = npc.regalosOdiados.ifBlank { "Sincroniza datos en el Home" })
                        
                        if (npc.posicion.isNotBlank()) {
                            DetailInfoRow(label = "🚶 Rutina", value = npc.posicion)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- SECCIÓN DE AMISTAD ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Amistad", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.Center) {
                            val fullHearts = npc.nivelAmistad / 250
                            repeat(10) { index ->
                                Icon(
                                    imageVector = if (index < fullHearts) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = if (index < fullHearts) Color(0xFFFF1744) else Color.Gray,
                                    modifier = Modifier.size(30.dp).clickable { viewModel.actualizarAmistad(npc.id, (index + 1) * 250) }
                                )
                            }
                        }
                        Text("${npc.nivelAmistad} / 2500 puntos", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- SECCIÓN DE SEGUIMIENTO ---
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(
                        modifier = Modifier.weight(1f).clickable { viewModel.toggleHabladoHoy(npc.id) },
                        colors = CardDefaults.cardColors(containerColor = if (npc.habladoHoy) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("¿Hablado?", style = MaterialTheme.typography.labelLarge)
                            Icon(
                                imageVector = if (npc.habladoHoy) Icons.Default.ChatBubble else Icons.Default.ChatBubbleOutline,
                                contentDescription = null,
                                tint = if (npc.habladoHoy) MaterialTheme.colorScheme.primary else Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Card(modifier = Modifier.weight(1.2f)) {
                        Column(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Regalos", style = MaterialTheme.typography.labelLarge)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(2) { index ->
                                    Icon(
                                        imageVector = if (index < npc.regalosSemanales) Icons.Default.Redeem else Icons.Default.CardGiftcard,
                                        contentDescription = null,
                                        tint = if (index < npc.regalosSemanales) Color(0xFF43A047) else Color.Gray,
                                        modifier = Modifier.size(34.dp).clickable { viewModel.actualizarRegalosSemanales(npc.id, index + 1) }
                                    )
                                }
                                if (npc.regalosSemanales > 0) {
                                    IconButton(onClick = { viewModel.actualizarRegalosSemanales(npc.id, 0) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.Clear, contentDescription = "Reset", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DetailInfoRow(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 20.sp
        )
    }
}
