package com.example.agendacontactosgrhr.view

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

//Función reusable para mostrar un mensaje temporal y navegar después de un retraso
suspend fun navegarConSnackbar(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    mensaje: String,
    ruta: String,
    delayMs: Long = 1500
){
    snackbarHostState.showSnackbar(mensaje)//Muestra el mensaje
    delay(delayMs)//Tiempo de espera
    navController.navigate(ruta)//Navega a la pantalla destino
}