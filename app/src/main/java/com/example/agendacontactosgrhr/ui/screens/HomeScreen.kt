package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.R
import com.example.agendacontactosgrhr.navigation.Screens

/**
 * Pantalla Inicio o Home.
 */
@Composable
fun HomeScreen(navController: NavHostController) {

    PantallaBase(
        titulo = "Índice",
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
                text = "Bienvenidx a MGSV",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // NPC Screen Image Button
            ImageMenuButton(
                resId = R.drawable.btn_npcs,
                contentDescription = "Aldeanos"
            ) {
                navController.navigate(Screens.NpcScreen.route)
            }

            Spacer(modifier = Modifier.height(18.dp)) // 12 * 1.5 = 18

            // Building Calculator Image Button
            ImageMenuButton(
                resId = R.drawable.btn_buildingcalculator,
                contentDescription = "Calculadora de edificios"
            ) {
                navController.navigate(Screens.BuildingCalculator.route)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Crop Calculator Image Button
            ImageMenuButton(
                resId = R.drawable.btn_cropcalculator,
                contentDescription = "Calculadora de cultivos"
            ) {
                navController.navigate(Screens.CropCalculator.route)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Crops Screen Image Button
            ImageMenuButton(
                resId = R.drawable.btn_crops,
                contentDescription = "Cultivos"
            ) {
                navController.navigate(Screens.CropScreen.route)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Favorites Screen Image Button
            ImageMenuButton(
                resId = R.drawable.btn_favorites,
                contentDescription = "Favoritos"
            ) {
                navController.navigate(Screens.FavScreen.route)
            }
        }
    }
}

@Composable
fun ImageMenuButton(resId: Int, contentDescription: String, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = Modifier
            .width(270.dp) // 180 * 1.5 = 270 (Calculated from other screens' width)
            .height(90.dp)  // 60 * 1.5 = 90
            .clickable(onClick = onClick)
    )
}
