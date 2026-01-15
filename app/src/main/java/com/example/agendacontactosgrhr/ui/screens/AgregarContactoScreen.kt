package com.example.agendacontactosgrhr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarContactoScreen(navController: NavHostController) {
    val viewModel: ContactosViewModel = hiltViewModel()
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(topBar = { TopAppBar(title = { Text("Nuevo Contacto") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
            Spacer(Modifier.height(24.dp))
            Button(onClick = {
                if (nombre.isNotBlank()) {
                    viewModel.insertarContacto(ContactoEntity(nombre = nombre, telefono = telefono))
                    Toast.makeText(context, "Contacto agregado", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }, modifier = Modifier.align(Alignment.End)) {
                Text("Guardar")
            }
        }
    }
}
