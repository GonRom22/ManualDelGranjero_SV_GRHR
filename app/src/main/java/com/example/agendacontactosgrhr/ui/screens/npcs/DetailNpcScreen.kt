package com.example.agendacontactosgrhr.ui.screens.npcs

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

/**
 * Esta pantalla recupera y muestra la info detallada de los contactos
 * utilizando un launchedeffect y basandose en la ID del contacto
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
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text("Nombre: ${npc.name}")
                Text("Cumpleaños: ${npc.cumpleanos}")
                Text("Estación: ${npc.estacionCumpleanos}")
                Text("Ubicación: ${npc.ubicacion}")
                Text("Regalos amados: ${npc.regalosAmados}")
                Text("Regalos odiados: ${npc.regalosOdiados}")
                Text("Amistad: ${npc.nivelAmistad}")
            }
        }
    }
}