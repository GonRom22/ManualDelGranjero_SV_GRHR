package com.example.agendacontactosgrhr.navigation

sealed class Screens(val route: String){
    object Login: Screens("login")
    object ListaContactos:Screens("listaContactos")
    object AgregarContacto:Screens("nuevoContacto")
    object EditarContacto:Screens("editarContacto")
    object DetalleContacto:Screens("detalleContacto")

}