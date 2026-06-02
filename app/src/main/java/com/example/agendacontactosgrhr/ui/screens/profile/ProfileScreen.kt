package com.example.agendacontactosgrhr.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.R
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()

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
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Nombre: ${userName ?: "Invitado"}",
                fontSize = 24.sp)
            // Podríamos añadir más datos si el SessionManager los guardara
            Text("Email: ${userName?.lowercase()?.replace(" ", ".")}@email.com",
                fontSize = 24.sp)

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de cerrar sesión btn_logout.png aumentado 1.5x (180*1.5=270, 60*1.5=90)
            Image(
                painter = painterResource(id = R.drawable.btn_logout),
                contentDescription = "Cerrar sesión",
                modifier = Modifier
                    .width(270.dp)
                    .height(90.dp)
                    .clickable {
                        viewModel.logout {
                            navController.navigate(Screens.Login.route) {
                                popUpTo(Screens.HomeScreen.route) { inclusive = true }
                            }
                        }
                    }
            )
        }
    }
}
