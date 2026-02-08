package com.example.agendacontactosgrhr.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleContactoScreen(navController: NavHostController, contactoId: Int) {

    val viewModel: ContactosViewModel = hiltViewModel()
    val context = LocalContext.current

    // Observación del estado del contacto seleccionado
    val contacto by viewModel.contactoSeleccionado.collectAsState()

    // Carga del contacto al iniciar o cambiar el ID
    LaunchedEffect(contactoId) {
        viewModel.obtenerContactoPorId(contactoId)
    }

    // Lógica del Intent para la Wiki
    val abrirWiki = {
        contacto?.let {
            val url = "https://stardewvalleywiki.com/${it.name.replace(" ", "_")}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Personaje") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            contacto?.let { character ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally // Corregido: horizontalAlignment
                ) {
                    Text(
                        text = "Nombre: ${character.name}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Teléfono: ${character.phone}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Botón para ejecutar el Intent de la Wiki
                    Button(
                        onClick = { abrirWiki() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null)
                        Spacer(Modifier.width(10.dp))
                        Text("Ver en la Wiki")
                    }
                }
            } ?: CircularProgressIndicator() // Feedback visual de carga
        }
    }
}