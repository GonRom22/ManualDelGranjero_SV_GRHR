package com.example.agendacontactosgrhr.ui.screens

import android.R.attr.id
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    //Recogemos el contexto actual de la activity para poder lanzar el intent
    val context  = LocalContext.current


    //Observamos el contacto seleccionado desde el ViewModel
    val contacto by viewModel.contactoSeleccionado.collectAsState()
    //Al cargar la pantalla, pedimos al ViewModel que busque el contacto por id
    LaunchedEffect(
        contactoId) {
        viewModel.obtenerContactoPorId(contactoId) }

    //Lógica del intent par abrir la wiki de cada personaje de stardew
    val abrirWiki = {
        contacto?.let {
            //Reemplaza los espacios en le nombre por guiones bajos para la URL de la wiki del juego
            val url = "https://stardewvalleywiki.com/${it.name.replace(" ", "_")}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
}
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Aligment.Center
        ){
            contacto?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((16.dp),
            horizontalAligment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Nombre: ${it.name}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                "Teléfono: ${it.phone}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(28.dp))

                            //Añadimos el botón para abrir la wiki
                            Button(
                                onClick = { abrirWiki() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null)
                                Spacer(Modifier.width(10.dp))
                                Text("Ver en la wiki")
                            }
                        }
            } ?: CircularProgressIndicator() //Nos da feedback visual de como va la carga
        }
        //Si contacto ya se cargó, muestra los datos
        contacto?.let {
            Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                Text("Nombre: ${it.name}")
                Text("Teléfono: ${it.phone}")
            }
            //Si no hay contacto, muestra indicador de carga
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()//Típico círculo que gira
        }
    }
}
