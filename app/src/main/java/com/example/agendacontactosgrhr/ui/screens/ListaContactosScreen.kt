package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaContactosScreen(navController: NavHostController) {
    val viewModel: ContactosViewModel = hiltViewModel()
    val contactos by viewModel.contactos.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Agenda de Contactos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.AgregarContacto.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        if (contactos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No hay contactos aún.")
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(padding)) {
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
