package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

/**
 * Pantalla que muestra la lista de contactos.
 *
 * Recibe:
 * - navController: para navegar entre pantallas (detalle, agregar, editar)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaContactosScreen(navController: NavHostController) {

    val viewModel: ContactosViewModel = hiltViewModel()
    val contactos by viewModel.contactos.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Agenda de Contactos") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AgregarContacto.route) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // usamos padding del Scaffold
        ) {
            // --- BOTÓN SUPERIOR ---
            Button(
                onClick = { viewModel.importarStardew() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.Backup, contentDescription = "Cargar NPCs Stardew Valley")
                Text(" Cargar PNJs")
            }

            // --- CONTENIDO ---
            if (contactos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay contactos aún.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(contactos) { contacto ->
                        ContactoItem(
                            contacto,
                            onEliminarClick = { viewModel.eliminarContacto(it) },
                            onEditarClick = { navController.navigate("${Screens.EditarContacto.route}/${it.id}") },
                            onVerDetalleClick = { navController.navigate("${Screens.DetalleContacto.route}/${it.id}") }
                        )
                    }
                }
            }
        }
    }
}

