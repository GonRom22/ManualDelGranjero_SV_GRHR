package com.example.agendacontactosgrhr.ui.screens.calculators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.ui.screens.PantallaBase

@Composable
fun BuildingCalculator(navController: NavHostController) {
    PantallaBase(
        titulo = "Calculadora de edificios",
        navController = navController
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Building Calculator Mockup")
        }
    }
}
