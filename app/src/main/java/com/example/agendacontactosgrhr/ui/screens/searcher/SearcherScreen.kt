package com.example.agendacontactosgrhr.ui.screens.searcher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.ui.screens.PantallaBase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearcherScreen(navController: NavHostController) {

    var textoBusqueda by remember { mutableStateOf("") }

    // Lista de ejemplo (puedes sustituir por contactos reales)
    val listaEjemplo = listOf("Abigail", "Lewis", "Harvey", "Maru", "Leah")

    val resultados = listaEjemplo.filter {
        it.contains(textoBusqueda, ignoreCase = true)
    }

    PantallaBase(
        titulo = "Buscar",
        navController = navController
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔍 Barra de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Resultados
            LazyColumn {
                items(resultados) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}