package com.example.agendacontactosgrhr.ui.screens.npcs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
        navController = navController
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
                // NPC Image - Centered and first thing shown
                npc.thumbnailResId?.takeIf { it != 0 }?.let { resId ->
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = npc.name,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

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
