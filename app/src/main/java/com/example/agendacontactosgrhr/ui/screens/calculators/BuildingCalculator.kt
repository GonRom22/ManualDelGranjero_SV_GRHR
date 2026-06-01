package com.example.agendacontactosgrhr.ui.screens.calculators

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.data.local.entity.EdificioEntity
import com.example.agendacontactosgrhr.data.remote.model.EdificioCalculo
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.CalculatorViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildingCalculator(navController: NavHostController) {
    val viewModel: CalculatorViewModel = hiltViewModel()
    val availableBuildings by viewModel.availableBuildings.collectAsState()
    val result by viewModel.buildingResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Escuchar mensajes de error
    LaunchedEffect(Unit) {
        viewModel.error.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Lista temporal para el patrón de acumulación
    val itemsToCalculate = remember { mutableStateListOf<Pair<EdificioEntity, EdificioCalculo>>() }

    var selectedBuilding by remember { mutableStateOf<EdificioEntity?>(null) }
    var cantidad by remember { mutableStateOf("1") }
    var expanded by remember { mutableStateOf(false) }

    PantallaBase(
        titulo = "Calculadora de Edificios",
        navController = navController,
        acciones = {
            if (itemsToCalculate.isNotEmpty()) {
                IconButton(onClick = { itemsToCalculate.clear() }) {
                    Icon(Icons.Default.DeleteSweep, contentDescription = "Limpiar lista")
                }
            }
            IconButton(onClick = { viewModel.syncBuildings() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Sincronizar Edificios")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Planificador de Construcción", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF5C3A1E))

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de Edificio (Exposed Dropdown)
                Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedBuilding?.nombre ?: "Selecciona un edificio",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Edificio") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                availableBuildings.forEach { building ->
                                    DropdownMenuItem(
                                        text = { Text(building.nombre) },
                                        onClick = { selectedBuilding = building; expanded = false }
                                    )
                                }
                            }
                        }

                        Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cantidad,
                                onValueChange = { if (it.all { char -> char.isDigit() }) cantidad = it },
                                label = { Text("Cant.") },
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            IconButton(
                                onClick = {
                                    selectedBuilding?.let { building ->
                                        itemsToCalculate.add(building to EdificioCalculo(building.id, cantidad.toIntOrNull() ?: 1))
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterVertically).background(Color(0xFF8B6914), MaterialTheme.shapes.small)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.White)
                            }
                        }
                    }
                }

                // Lista de edificios añadidos para calcular
                LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
                    items(itemsToCalculate) { item ->
                        ListItem(
                            headlineContent = { Text(item.first.nombre) },
                            supportingContent = { Text("${item.second.cantidad} unidades") },
                            trailingContent = {
                                IconButton(onClick = { itemsToCalculate.remove(item) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }

                if (itemsToCalculate.isNotEmpty()) {
                    Button(
                        onClick = { viewModel.calcularEdificios(itemsToCalculate.map { it.second }) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C3A1E)),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("CALCULAR RECURSOS")
                        }
                    }
                }

                // Muestra de resultados finales
                result?.let { res ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF6E3)),
                        border = BorderStroke(1.dp, Color(0xFF8B6914))
                    ) {
                        Column(Modifier.padding(16.dp).fillMaxWidth()) {
                            Text("TOTAL DE MATERIALES", fontWeight = FontWeight.Bold, color = Color(0xFF5C3A1E))
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF8B6914).copy(alpha = 0.3f))
                            
                            ResourceRow("Oro total", "${res.totalOro}g")
                            if (res.totalMadera > 0) ResourceRow("Madera", "${res.totalMadera}")
                            if (res.totalPiedra > 0) ResourceRow("Piedra", "${res.totalPiedra}")
                            if (res.totalArcilla > 0) ResourceRow("Arcilla", "${res.totalArcilla}")
                            if (res.totalFibra > 0) ResourceRow("Fibra", "${res.totalFibra}")
                            if (res.totalMaderaNoble > 0) ResourceRow("Madera Noble", "${res.totalMaderaNoble}")
                            if (res.totalLingoteCobre > 0) ResourceRow("Lingote Cobre", "${res.totalLingoteCobre}")
                            if (res.totalLingoteHierro > 0) ResourceRow("Lingote Hierro", "${res.totalLingoteHierro}")
                            if (res.totalLingoteIridio > 0) ResourceRow("Lingote Iridio", "${res.totalLingoteIridio}")
                            if (res.totalCuarzoRefinado > 0) ResourceRow("Cuarzo Refinado", "${res.totalCuarzoRefinado}")
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

@Composable
fun ResourceRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.DarkGray)
        Text(value, fontWeight = FontWeight.Bold, color = Color(0xFF5C3A1E))
    }
}
