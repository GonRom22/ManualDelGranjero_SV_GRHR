package com.example.agendacontactosgrhr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ItemContact(
    name:String,
    phone:String,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,//Pasamos el esrtado del Snackbar
    onClick:() -> Unit//Le pasamos función onCLick como parámetro, no recibe nada ni devuelve nada
) {
    val scope = rememberCoroutineScope ()//para que se recuerde

    //Función para mostrar el snackbar y trtansición
    fun mostrarMensajeYNavegar(){
        scope.launch{
            //Avisamos al ViewModel
            onClick()
            //Mostramos el Snackbar
            snackbarHostState.showSnackbar(message= "Navegando a pantalla detalle de: $name")
            //Esperamos un poco para que de tiempop a ser leído
            delay(1500)//segundo y medio
            //navegamos a la pantalla detalle
            navController.navigate("detail/$name")
        }
    }

    //Esta fila muestra un nombre y un teléfono..
    Row (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        //Nombre clicable
        Text(name,
            fontSize = 24.sp,
            fontWeight = Bold,
            color = Color.DarkGray,
            modifier = Modifier.clickable { mostrarMensajeYNavegar()}//sustituye lo de abajo que ya tenemos en la funcuion de arriba
                /*scope.launch{
                    onClick()//llama al ViewModel
                    //Mostramos el mensaje temporal
                    snackbarHostState.showSnackbar("Seleccionaste: $name")
                    //Añadimos un ligero retraso para que sea visible el mensaje
                    kotlinx.coroutines.delay(800)
                    navController.navigate("detail/$name")//Navega a Detail con el nombre.
                }*/

            )
        //Teléfono e icono de navegación
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(
                phone,
                fontSize = 22.sp,
                modifier = Modifier.padding(end=12.dp),
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Detalle",
                tint = Color.Blue,
                modifier = Modifier.clickable{mostrarMensajeYNavegar()}
                    /*scope.launch{
                        onClick()
                        snackbarHostState.showSnackbar("Seleccionaste: $name")
                        kotlinx.coroutines.delay(800)
                        navController.navigate("detail/$name")//Navega a Detail con el icono
                    }*/

            )
        }
    }
}