package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens

/**
 * Pantalla Inicio o Home.
 *
 * Recibe:
 * - navController: para navegar entre pantallas (detalle, agregar, editar)
 */
@Composable
fun HomeScreen(navController: NavHostController) {

    PantallaBase(
        titulo = "Home",
        navController = navController
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Bienvenido a la app")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate(Screens.ListaContactos.route)
            }) {
                Text("Ir a contactos")
            }

            Button(onClick = {
                navController.navigate(Screens.NpcScreen.route)
            }) {
                Text("Ver NPCs")
            }
        }
    }
}
