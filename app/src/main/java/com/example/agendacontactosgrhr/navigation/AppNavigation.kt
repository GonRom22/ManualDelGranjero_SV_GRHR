package com.example.agendacontactosgrhr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendacontactosgrhr.ui.screens.login.LoginScreen
import com.example.agendacontactosgrhr.ui.screens.HomeScreen
import com.example.agendacontactosgrhr.ui.screens.npcs.DetailNpcScreen
import com.example.agendacontactosgrhr.ui.screens.npcs.NpcScreen
import com.example.agendacontactosgrhr.ui.screens.profile.ProfileScreen
import com.example.agendacontactosgrhr.ui.screens.searcher.SearcherScreen

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

    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {

        // 🔐 LOGIN
        composable(Screens.Login.route) {
            LoginScreen(navController)
        }

        // 🏠 HOME
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController)
        }

        // 🌱 NPC LISTA
        composable(Screens.NpcScreen.route) {
            NpcScreen(navController)
        }

        // 🌱 NPC DETALLE
        composable(
            route = "${Screens.DetailNpc.route}/{npcId}",
            arguments = listOf(navArgument("npcId") {
                type = NavType.IntType
            })
        ) { entry ->
            val id = entry.arguments?.getInt("npcId") ?: 0
            DetailNpcScreen(navController, id)
        }

        // 🔍 BÚSQUEDA
        composable(Screens.Search.route) {
            SearcherScreen(navController)
        }

        // 👤 PERFIL
        composable(Screens.Profile.route) {
            ProfileScreen(navController)
        }
    }
}