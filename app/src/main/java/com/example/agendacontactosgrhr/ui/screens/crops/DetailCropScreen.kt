package com.example.agendacontactosgrhr.ui.screens.crops

import androidx.compose.foundation.layout.*
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
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.CropsViewModel

@Composable
fun DetailCropScreen(
    navController: NavHostController,
    cropId: Int
) {
    val viewModel: CropsViewModel = hiltViewModel()
    val crops by viewModel.crops.collectAsState()
    val crop = crops.firstOrNull { it.id == cropId }

    PantallaBase(
        titulo = crop?.nombre ?: "Detalle del Cultivo",
        navController = navController
    ) { padding ->
        if (crop == null) {
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = crop.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DetailRow(label = "Temporada", value = crop.temporada)
                        DetailRow(label = "Precio de Semilla", value = "${crop.precioSemilla} g")
                        DetailRow(label = "Precio de Venta", value = "${crop.precioVenta} g")
                        DetailRow(label = "Tiempo de Crecimiento", value = "${crop.tiempoCrecimiento} días")
                        
                        if (crop.tiempoRegreso > 0) {
                            DetailRow(label = "Tiempo de Regreso", value = "${crop.tiempoRegreso} días")
                        }

                        Divider()

                        // Cálculo de beneficio simple
                        val beneficio = crop.precioVenta - crop.precioSemilla
                        DetailRow(label = "Beneficio por cosecha", value = "${beneficio} g")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.SemiBold)
        Text(text = value)
    }
}
