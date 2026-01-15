package com.example.agendacontactosgrhr.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendacontactosgrhr.ui.screens.AgregarContactoScreen
import com.example.agendacontactosgrhr.ui.screens.EditarContactoScreen
import com.example.agendacontactosgrhr.ui.screens.ListaContactosScreen
import com.example.agendacontactosgrhr.ui.screens.DetalleContactoScreen
import com.example.agendacontactosgrhr.ui.screens.LoginScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.Login.route) {
        composable(Screens.Login.route) {
            LoginScreen(navController)
        }

        // Lista de contactos
        composable(Screens.ListaContactos.route) {
            ListaContactosScreen(navController)
        }

        composable(Screens.AgregarContacto.route) {
            AgregarContactoScreen(navController)
        }
        composable(
            route = "${Screens.EditarContacto.route}/{contactoId}",
            arguments = listOf(navArgument("contactoId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("contactoId") ?: 0
            EditarContactoScreen(navController, id)
        }
        composable(
            route = "${Screens.DetalleContacto.route}/{contactoId}",
            arguments = listOf(navArgument("contactoId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("contactoId") ?: return@composable
            DetalleContactoScreen(navController, id)
        }
    }
}