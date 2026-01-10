package com.example.agendacontactosgrhr

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendacontactosgrhr.screens.DetailScreen
import com.example.agendacontactosgrhr.screens.HomeScreen
import com.example.agendacontactosgrhr.screens.LoginScreen

@Composable
fun NavigatorHostController() {
    val navController = rememberNavController()//Controlador de navegación.
    //Aquí definimos todas las rutas de la app.
    NavHost(
        navController = navController,
        startDestination = "login",//Se inicia aquí.
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = TweenSpec(700))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = TweenSpec(700))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = TweenSpec(700))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = TweenSpec(700))
        }
    ) {
        //Rutas.
        composable ("login"){LoginScreen(navController)}
        composable ("home"){HomeScreen(navController)}//Ruta home lleva a la función homescreen
        composable (
            "detail/{nombre}",
            arguments = listOf(
                navArgument("nombre"){
                    type = NavType.StringType
                }
            )
        )
        {navPopBackEntry ->
            val nombre = navPopBackEntry.arguments?.getString("nombre")//Aquí obtenemos el parámetro nombre.
            DetailScreen(navController, nombre)}
    }

}