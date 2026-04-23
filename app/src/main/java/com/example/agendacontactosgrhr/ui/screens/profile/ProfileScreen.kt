package com.example.agendacontactosgrhr.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase

@Composable
fun ProfileScreen(navController: NavHostController) {

    PantallaBase(
        titulo = "Perfil",
        navController = navController
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Mi Perfil",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Nombre: Usuario Ejemplo")
            Text("Email: usuario@email.com")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                // Volver al login (ejemplo de logout)
                navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.HomeScreen.route) { inclusive = true }
                }
            }) {
                Text("Cerrar sesión")
            }
        }
    }
}