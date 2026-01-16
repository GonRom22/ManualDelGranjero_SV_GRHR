package com.example.agendacontactosgrhr.navigation

/**
 * Aquí definimos todas las pantallas de nuestra app.
 *
 * Cada pantalla tiene una “ruta” (route) que usamos para navegar.
 * La idea es no poner nombres raros de ruta en varios lugares,
 * sino tenerlos aquí para que sea más fácil cambiar.
 */
sealed class Screens(val route: String){

    //Pantalla de Login
    object Login: Screens("login")

    //Pantalla de la lista de contactos
    object ListaContactos:Screens("listaContactos")

    //Pantalla para agregar un contacto nuevo
    object AgregarContacto:Screens("nuevoContacto")

    //PAntalla para editar un contacto existente
    object EditarContacto:Screens("editarContacto")

    //Pantalla para ver los detalles de un contacto
    object DetalleContacto:Screens("detalleContacto")
}