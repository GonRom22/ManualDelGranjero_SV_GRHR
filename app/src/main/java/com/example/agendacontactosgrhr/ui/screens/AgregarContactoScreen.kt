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

    //Creamos el ViewModel usando Hilt
    val viewModel: ContactosViewModel = hiltViewModel()

    //Variables para guardar lo que escribe el usuario
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    //Contexto de Android para mostrar Toast
    val context = LocalContext.current

    //El Scaffold permite diseñar la disposición de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nuevo Contacto") })
        }
    ) { padding -> //padding viene del Scaffold y evita que se superponda con la barra
        Column(
            modifier = Modifier
                .fillMaxSize()//Toda la pantalla
                .padding(padding)//padding de Scaffold
                .padding(16.dp)//padding extra
        ) {
            //Campo para el nombre
            OutlinedTextField(
                name,
                onValueChange = { name = it },
                label = { Text("Nombre") }
            )

            Spacer(Modifier.height(16.dp))//Espacio extra entre campos

            //Campo para escribir el tlf
            OutlinedTextField(
                phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") }

            )

            Spacer(Modifier.height(24.dp))

            //Botón de guardar
            Button(
                onClick = {
                    //Comprueba si no está vacío
                if (name.isNotBlank()) {
                    viewModel.insertarContacto(
                        ContactoEntity(
                            title = "", name = name, lastName = "", phone = phone, email = "", city = "",
                            country = "", thumbnail = ""





                        )
                    )
                    //Muestra mensaje al usuario
                    Toast.makeText(context, "Contacto agregado", Toast.LENGTH_SHORT).show()
                    //Vuelve atrás, a la pantalla anterior
                    navController.popBackStack()
                }
            },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Guardar")
            }
        }
    }
}
