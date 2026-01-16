package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

/**
 * Pantalla que muestra los detalles de un contacto.
 *
 * Recibe:
 * - navController: para poder volver a la pantalla anterior
 * - contactoId: el id del contacto que queremos mostrar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleContactoScreen(navController: NavHostController, contactoId: Int) {

    //Obtenemos el ViewModel usando Hilt
    val viewModel: ContactosViewModel = hiltViewModel()
    //Observamos el contacto seleccionado desde el ViewModel
    val contacto by viewModel.contactoSeleccionado.collectAsState()
    //Al cargar la pantalla, pedimos al ViewModel que busque el contacto por id
    LaunchedEffect(Unit) { viewModel.obtenerContactoPorId(contactoId) }

    //Scaffold dispone el diseño de pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Contacto") },
                navigationIcon = {
                    //Botón para regresar
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        //Si contacto ya se cargó, muestra los datos
        contacto?.let {
            Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                Text("Nombre: ${it.nombre}")
                Text("Teléfono: ${it.telefono}")
            }
            //Si no hay contacto, muestra indicador de carga
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()//Típico círculo que gira
        }
    }
}
