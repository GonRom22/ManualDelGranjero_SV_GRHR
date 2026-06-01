package com.example.agendacontactosgrhr.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.R
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.theme.StardewGreen
import com.example.agendacontactosgrhr.viewmodel.LoginViewModel

/**
 * Pantalla de Login
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StardewGreen,
                    titleContentColor = Color.White
                ),
                title = { Text("Manual del Granjero: Stardew Valley") }
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
                    color = MaterialTheme.colorScheme.onBackground)
            }

            HorizontalDivider()

            //modificamos para que coincida con los campos de la API
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.padding(16.dp),
                label = { Text("Email") },
                singleLine = true
            )

            //Contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
                label = {Text("Password")},
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                visualTransformation =
                    if(viewModel.showPassword) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.togglePasswordVisibility()
                        }
                    ) {
                        Icon(
                            if (viewModel.showPassword)
                                Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Mostrar contraseña"
                        )
                    }
                }
            )

            //Muestra el error de la API (credenciales incorrectas, sin conexion, etc)
            viewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Botón personalizado btn_login.png aumentado 1.5x (180*1.5=270, 60*1.5=90)
            Image(
                painter = painterResource(id = R.drawable.btn_login),
                contentDescription = "Login",
                modifier = Modifier
                    .padding(16.dp)
                    .width(270.dp)
                    .height(90.dp)
                    .clickable {
                        viewModel.login {

                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(Screens.Login.route) { inclusive =
                                    true }
                            }
                        }
                    }
            )

            HorizontalDivider()
        }
    }
}