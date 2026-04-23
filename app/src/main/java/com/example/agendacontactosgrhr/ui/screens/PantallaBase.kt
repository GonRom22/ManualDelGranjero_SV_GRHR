package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBase(
    titulo: String,
    navController: NavHostController,
    contenido: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screens.HomeScreen.route) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(Screens.Search.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    },
                    label = { Text("Buscar") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screens.Profile.route) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(Icons.Default.Person, contentDescription = "Perfil")
                },
                label = { Text("Perfil") }
                )
            }
        }
    ) { padding ->
        contenido(padding)
    }
}
