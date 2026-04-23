package com.example.agendacontactosgrhr.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bungalow
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.viewmodel.LoginViewModel

/**
 * Pantalla de Login
 *
 * Recibe:
 * - navController: para navegar a la lista de contactos cuando el login es correcto
 * - viewModel: contiene los datos de usuario, contraseña y funciones de login
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel : LoginViewModel = viewModel()
) {
    //Scaffold para diseñar y distribuir la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.DarkGray),
                title = { Text("Login Screen",
                    color = Color.White) }
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Título de pantalla
            Row(modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically){

            Text("Inicio de sesión",
                fontSize = 24.sp,
                color = Color.Black)
            }

            HorizontalDivider()

            //Texto de Usuario
            OutlinedTextField(
                //Texto acutal
                value = viewModel.userName,
                //Función que actualiza el ViewModel
                onValueChange = viewModel::onUserNameChange,
                modifier = Modifier.padding(16.dp),
                label = { Text("Introduce tu nombre") },
                singleLine = true
            )

            //Contraseña
            OutlinedTextField(
                //Contraseña actual
                value = viewModel.password,
                //Función que actualiaza el ViewModel
                onValueChange = viewModel::onPasswordChange,
                label = {Text("Password")},
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                //Si showPassword = true se ve, si no se ocultará
                visualTransformation =
                if(viewModel.showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                //Icono para mostrar u ocultar la contraseña
                trailingIcon = {
                    IconButton(
                        onClick = {
                            //Si showPassword es true cambia a false y viceversa
                            viewModel.togglePasswordVisibility()
                        }
                    ) {
                        //Seleccionamos iconos diferentes para mostrar u ocultar
                        // contraseña
                        Icon(
                            if (viewModel.showPassword)
                                Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Mostrar contraseña"
                        )
                    }
                }
            )

            //Botón para aceptar i logearse
            IconButton(
                onClick = {
                    //Si login es correcto
                    viewModel.login {
                        navController.navigate(Screens.HomeScreen.route) {
                            //Elimina la pantalla de login de la pila para que no se pueda volver atrás
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    }
                }
            ){
                Icon(Icons.Default.Bungalow, contentDescription = "Login")
            }

            HorizontalDivider()
        }
    }
}