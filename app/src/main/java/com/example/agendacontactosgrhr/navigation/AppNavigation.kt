package com.example.agendacontactosgrhr.navigation

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

/**
 * Función que define la navegación de la aplicación usando Jetpack Compose.
 *
 * - NavHost: Contenedor que gestiona las rutas y pantallas.
 * - navController: Controlador que permite moverse entre pantallas.
 * - startDestination: Pantalla inicial al abrir la app (LoginScreen en este caso).
 *
 * Cada composable define una “pantalla” y la ruta que se usa para navegar a ella.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.Login.route) {

        //Pantalla de Login
        composable(Screens.Login.route) {
            LoginScreen(navController)
        }

        // Lista de contactos
        composable(Screens.ListaContactos.route) {
            ListaContactosScreen(navController)
        }

        //Pantalla para agregar nuevo contacto
        composable(Screens.AgregarContacto.route) {
            AgregarContactoScreen(navController)
        }

        //Pantalla para editar contacto existente
        //Recibe un parámetro "contactoId" por ruta
        composable(
            route = "${Screens.EditarContacto.route}/{contactoId}",
            arguments = listOf(navArgument("contactoId") { type = NavType.IntType })
        ) { entry ->
            //Obtenemos el id del contacto a editar
            val id = entry.arguments?.getInt("contactoId") ?: 0
            EditarContactoScreen(navController, id)
        }

        //Pantalla que muestra los detalles de un contacto
        composable(
            route = "${Screens.DetalleContacto.route}/{contactoId}",
            arguments = listOf(navArgument("contactoId") { type = NavType.IntType })
        ) { entry ->//entry (navBackStackEntry)es un objeto que contiene la información de la navegación hacia la pantalla
            //Obtenemos el id del contacto a mostrar
            val id = entry.arguments?.getInt("contactoId") ?: return@composable
            DetalleContactoScreen(navController, id)
        }
    }
}