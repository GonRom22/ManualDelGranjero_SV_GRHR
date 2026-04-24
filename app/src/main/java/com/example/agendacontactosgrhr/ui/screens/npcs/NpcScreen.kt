package com.example.agendacontactosgrhr.ui.screens.npcs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@Composable
fun NpcScreen(navController: NavHostController) {
    val viewModel: ContactosViewModel = hiltViewModel()
    val npcs by viewModel.contactos.collectAsState()

    PantallaBase(
        titulo = "Residentes de Pueblo Pelícano",
        navController = navController
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(npcs) { npc ->
                    NpcItem(npc = npc) {
                        navController.navigate("${Screens.DetailNpc.route}/${npc.id}")
                    }
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
    }
}

@Composable
fun NpcItem(npc: com.example.agendacontactosgrhr.data.local.entity.ContactoEntity, onClick: () -> Unit) {
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

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Portrait of ${npc.name}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
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
        }
    }
}
