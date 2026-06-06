package com.example.agendacontactosgrhr.ui.screens.calculators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.data.local.entity.CropEntity
import com.example.agendacontactosgrhr.data.remote.model.CultivoCalculo
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.CalculatorViewModel
import com.example.agendacontactosgrhr.viewmodel.CropsViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropCalculator(navController: NavHostController) {
    val viewModel: CalculatorViewModel = hiltViewModel()
    val cropsViewModel: CropsViewModel = hiltViewModel()
    val availableCrops by viewModel.availableCrops.collectAsState()
    val result by viewModel.cropResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Escuchar mensajes de error y éxito
    LaunchedEffect(Unit) {
        viewModel.error.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
        cropsViewModel.uiEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Estado local para construir la lista de cálculo
    val itemsToCalculate = remember { mutableStateListOf<Pair<CropEntity, CultivoCalculo>>() }
    
    var selectedCrop by remember { mutableStateOf<CropEntity?>(null) }
    var cantidad by remember { mutableStateOf("10") }
    var harvests by remember { mutableStateOf("1") }
    var expanded by remember { mutableStateOf(false) }

    PantallaBase(titulo = "Calculadora de Cultivos", navController = navController, acciones = {
        if (itemsToCalculate.isNotEmpty()) {
            IconButton(onClick = { itemsToCalculate.clear() }) {
                Icon(Icons.Default.DeleteSweep, contentDescription = "Limpiar lista")
            }
        }
        IconButton(onClick = { cropsViewModel.syncCrops() }) {
            Icon(Icons.Default.Refresh, contentDescription = "Sincronizar Cultivos")
        }
    }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Planificador de Cosecha", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF2D5A27))
                
                Spacer(modifier = Modifier.height(16.dp))

                // Selector y controles
                Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedCrop?.nombre ?: "Selecciona un cultivo",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Cultivo",
                                    fontSize = 18.sp) },
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 20.sp
                                ),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                availableCrops.forEach { crop ->
                                    DropdownMenuItem(
                                        text = { Text(crop.nombre,
                                            fontSize = 16.sp) },
                                        onClick = { selectedCrop = crop; expanded = false }
                                    )
                                }
                            }
                        }

                        Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cantidad,
                                onValueChange = { cantidad = it },
                                label = { Text("Cant.",
                                    fontSize = 18.sp) },
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = harvests,
                                onValueChange = { harvests = it },
                                label = { Text("Cosechas",
                                    fontSize = 18.sp) },
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            IconButton(
                                onClick = {
                                    selectedCrop?.let { crop ->
                                        itemsToCalculate.add(crop to CultivoCalculo(crop.id, cantidad.toIntOrNull() ?: 0, harvests.toIntOrNull() ?: 1))
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterVertically).background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.White)
                            }
                        }
                    }
                }

                // Lista de elementos añadidos
                LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
                    items(itemsToCalculate) { item ->
                        ListItem(
                            headlineContent = { Text(item.first.nombre, fontSize = 24.sp)},
                            supportingContent = { Text("${item.second.cantidad} unidades x ${item.second.cosechas} cosechas", fontSize = 18.sp) },
                            trailingContent = {
                                IconButton(onClick = { itemsToCalculate.remove(item) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }

                // Botón de calcular y resultados
                if (itemsToCalculate.isNotEmpty()) {
                    Button(
                        onClick = { viewModel.calcularCultivos(itemsToCalculate.map { it.second }) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        Text(if (isLoading) "Calculando..." else "CALCULAR TOTALES",
                            fontSize = 16.sp)
                    }
                }

                result?.let { res ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
                        Column(Modifier.padding(16.dp).fillMaxWidth()) {
                            Text("RESUMEN DE GANANCIAS", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D5A27))
                            Text("Ganancia Bruta: ${res.gananciaTotal}g", fontSize = 20.sp)
                            Text("Coste Semillas: ${res.costeSemillas}g", fontSize = 20.sp)
                            Text("Beneficio Neto: ${res.beneficioNeto}g", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2D5A27))
                        }
                    }
                }
            }
            
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
