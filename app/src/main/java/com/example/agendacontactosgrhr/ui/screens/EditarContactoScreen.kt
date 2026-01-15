package com.example.agendacontactosgrhr.ui.screens

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarContactoScreen(navController: NavHostController, contactoId: Int) {
    val viewModel: ContactosViewModel = hiltViewModel()
    val contacto by viewModel.contactoSeleccionado.collectAsState()
    var nombre by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var datosCargados by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.obtenerContactoPorId(contactoId) }
    LaunchedEffect(contacto) {
        if (contacto != null && !datosCargados) {
            nombre = contacto!!.nombre
            telefono = contacto!!.telefono
            datosCargados = true
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Editar Contacto") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
            Spacer(Modifier.height(24.dp))
            Button(onClick = {
                contacto?.let {
                    viewModel.actualizarContacto(it.copy(nombre = nombre, telefono = telefono))
                    Toast.makeText(context, "Contacto actualizado", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Actualizar")
            }
        }
    }
}
