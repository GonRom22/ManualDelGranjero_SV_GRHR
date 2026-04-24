package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens

/**
 * Pantalla Inicio o Home.
 */
@Composable
fun HomeScreen(navController: NavHostController) {

    PantallaBase(
        titulo = "MGSV Prototype Home",
        navController = navController
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Bienvenido a MGSV Prototype",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // NPC Screen Button
            MenuButton(text = "Aldeanos") {
                navController.navigate(Screens.NpcScreen.route)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Building Calculator Button
            MenuButton(text = "Calculadora de edificios") {
                navController.navigate(Screens.BuildingCalculator.route)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Crop Calculator Button
            MenuButton(text = "Calculadora de cultivos") {
                navController.navigate(Screens.CropCalculator.route)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Crops Screen Button
            MenuButton(text = "Cultivos") {
                navController.navigate(Screens.CropScreen.route)
            }
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(text)
    }
}
