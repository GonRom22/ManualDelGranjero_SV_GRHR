package com.example.agendacontactosgrhr.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//Le pasamos el controlador de navegación y el view model entre ().
fun HomeScreen(navController: NavHostController,
               viewModel: ContactosViewModel = viewModel()
) {
    //Con collectAsState() -> recolectamos el flujo de contactos como un estado para que el
    // @Composable se reactive automáticamente al cambiar de datos/estado
    val contactos by viewModel.contactosFlow.collectAsState()

    //Aquí el estado del Snackbar que controla qué mensaje se muestra.
    //indicamos dónde se dibuja el Snackbar y qué estado usa
    val snackbarHostState = remember{ SnackbarHostState() }

    //LaunchedEffect se lanza una sola vez y escucha los eventos del ViewModel
    //Dichos eventos NO se repiten al rotar la pantalla
    //Creamos una corrutina LaunchedEffect para escuchar el flujo de eventos del viewModel (eventoUI)
    // y pasamos "Unit" como parámetro para que se ejecute solo una vez Una vez se lanza la corrutina,
    // esta sigue activa mientras el composable esté activo. Es decir, si el composable se recompone
    // porque cambia el estado, el LauncheEffect no se volvera a lanzar pero el flujo se sigue
    // recolectando.
    // Dentro de LaunchedEffect estamos recolectando el flujo del eventoUI del ViewModel: indicamos el viewModel, accedemos al evento
    //y usamos el metodito collect que nos va a devolver el evento (mensaje).
    //El metodito collect devuelve el evento y se va a quedar escuchando permanentemente en el
    // SharedFlow, devolviendo un mensaje nuevo (evento) cada vez que se genere.
    //Luego pasamos el mensaje al controlador de estado de Snackbar (snackbarHostState) para que lo
    // muestre.
    LaunchedEffect(Unit){
        viewModel.eventoUI.collect {mensaje ->
        snackbarHostState.showSnackbar(mensaje)
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.Gray),
                title = {
                    Text("Home",
                        color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        //Aquí indicamos dónde se dibuja el Snackbar
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ){  paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            contactos.forEach { contacto ->
                ItemContact(
                    name = contacto.nombre,
                    phone = contacto.telefono,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    //Avisamos al ViewModel de que se ha pulsado un contacto
                    onClick = {
                        viewModel.onContactoSeleccionado(contacto.nombre)
                    }
                )
            }
        }
    }
}