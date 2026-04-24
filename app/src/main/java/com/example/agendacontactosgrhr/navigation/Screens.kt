package com.example.agendacontactosgrhr.navigation

/**
 * Aquí definimos todas las pantallas de nuestra app.
 */
sealed class Screens(val route: String){

    //Pantalla de Login
    object Login: Screens("login")

    //Pantalla de Home
    object HomeScreen:Screens("home")

    //Pantalla de la lista de contactos (OBSOLETO - pero mantenido por si acaso)
    object ListaContactos:Screens("listaContactos")

    //Pantalla para agregar un contacto nuevo
    object AgregarContacto:Screens("nuevoContacto")

    //PAntalla para editar un contacto existente
    object EditarContacto:Screens("editarContacto")

    //Pantalla para ver los detalles de un contacto
    object DetalleContacto:Screens("detalleContacto")

    object Search:Screens("search")

    object Profile:Screens("profile")

    object NpcScreen: Screens("npc")

    object DetailNpc: Screens("detalleNpc")

    // NUEVAS PANTALLAS
    object BuildingCalculator: Screens("buildingCalculator")
    object CropCalculator: Screens("cropCalculator")
    object CropScreen: Screens("cropScreen")
    object FavScreen: Screens("favScreen")
}
