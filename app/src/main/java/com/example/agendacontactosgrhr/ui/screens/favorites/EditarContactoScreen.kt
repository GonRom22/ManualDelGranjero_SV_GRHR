package com.example.agendacontactosgrhr.ui.screens.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

/**
 * Pantalla para editar un contacto existente.
 *
 * Recibe:
 * - navController: para volver a la pantalla anterior
 * - contactoId: id del contacto que queremos editar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarContactoScreen(navController: NavHostController, contactoId: Int) {

    //Obtenemos ViewModel
    val viewModel: ContactosViewModel = hiltViewModel()

    //Observa el contacto desde el ViewModel
    val contacto by viewModel.contactoSeleccionado.collectAsState()

    //Variables que guardan datos del contactro que se mostrarán y editarán
    var nombre by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    //Controla si ya se cargaron los datos para no sobreescribirlos
    var datosCargados by remember { mutableStateOf(false) }
    //Contexto Android para mostrar Toast
    val context = LocalContext.current
    //Al iniciar la pantalla, pedimos a ViewModel que busque el contascto por id
    LaunchedEffect(Unit) { viewModel.obtenerContactoPorId(contactoId) }
    //Cuando el contacto se cambia se rellenan los campos de texto una vez
    LaunchedEffect(contacto) {
        if (contacto != null && !datosCargados) {
            nombre = contacto!!.name
            telefono = contacto!!.phone
            datosCargados = true
        }
    }

    //Scaffold para diseño de pantalla
    Scaffold(
        topBar = { TopAppBar(title = { Text("Editar Contacto") }) }
    ) { padding ->
        //Columna con campos y botón
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            //Campo para nombre
            OutlinedTextField(
                nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") }
            )

            Spacer(Modifier.height(16.dp))

            //Campo para tlf
            OutlinedTextField(
                telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") }
            )

            Spacer(Modifier.height(24.dp))

            //Botón
            Button(
                onClick = {
                contacto?.let {
                    //Se crea una copia del contacto con nuevos datos
                    viewModel.actualizarContacto(it.copy(name = nombre, phone = telefono))
                    Toast.makeText(context, "Contacto actualizado", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()//Se regresa a la pantalla anterior
                }
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar")
            }
        }
    }
}
