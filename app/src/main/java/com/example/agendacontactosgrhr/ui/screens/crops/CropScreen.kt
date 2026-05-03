package com.example.agendacontactosgrhr.ui.screens.crops

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.CropsViewModel

@Composable
fun CropScreen(navController: NavHostController) {
    val viewModel: CropsViewModel = hiltViewModel()
    val crops by viewModel.crops.collectAsState()

    PantallaBase(
        titulo = "Cultivos de Stardew Valley",
        navController = navController
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(crops) { crop ->
                    CropItem(crop = crop) {
                        navController.navigate("${Screens.DetailCrop.route}/${crop.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun CropItem(crop: CropEntity, onClick: () -> Unit) {
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
