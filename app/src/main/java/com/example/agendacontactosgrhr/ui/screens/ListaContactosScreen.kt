package com.example.agendacontactosgrhr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
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

    //Obtenemos ViewModel usando Hilt
    val viewModel: ContactosViewModel = hiltViewModel()
    //observamos la lista de contactos desde el ViewModel
    val contactos by viewModel.contactos.collectAsState()
    //Scaffold distribuye la pantalla en topbar, contenido y floating button
    Scaffold(
        topBar = { TopAppBar(title = { Text("Agenda de Contactos") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //Navega a agregar contacto
                    navController.navigate(Screens.AgregarContacto.route)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        //Si no hay contactos se muestra mensaje centrado
        if (contactos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay contactos aún.")
            }
            //Si hay contactos se muestra la lista
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    //Recorremos cada contacto y lo mostramos
                    items(contactos) { contacto ->
                        ContactoItem(
                            contacto,
                            //Función para borrar
                            onEliminarClick = { viewModel.eliminarContacto(it) },
                            //Función para editar
                            onEditarClick = { navController.navigate("${Screens.EditarContacto.route}/${it.id}") },
                            //Función para ver detalle
                            onVerDetalleClick = { navController.navigate("${Screens.DetalleContacto.route}/${it.id}") }
                        )
                    }
                }
                //Botón de guardar
                Button(
                    onClick = {
                        viewModel.loadContactoAPI()

                    },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.Backup,
                        contentDescription = "Cargar Contacto"
                    )
                }
            }
        }
    }
}
