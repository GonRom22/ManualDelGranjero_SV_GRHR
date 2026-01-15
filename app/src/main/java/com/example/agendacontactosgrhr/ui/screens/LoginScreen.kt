package com.example.agendacontactosgrhr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bungalow
import androidx.compose.material.icons.filled.Chalet
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel : LoginViewModel = viewModel()
) {
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
            Row(modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically){

            Text("Inicio de sesión",
                fontSize = 24.sp,
                color = Color.Black)
            }

            HorizontalDivider()

            //Texto de Usuario
           // var textName by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                value = viewModel.userName,
                onValueChange = viewModel::onUserNameChange,
                modifier = Modifier.padding(16.dp),
                label = { Text("Introduce tu nombre") },
                //value = textName,
                //onValueChange = { newValue: String -> textName = newValue },
                singleLine = true
            )

            //Contraseña
            //var textPassword by rememberSaveable { mutableStateOf("") }

            //Variable que controla si mostramos u ocultamos contraseña
            //var showPassword by rememberSaveable { mutableStateOf(false) }

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
                    IconButton(onClick = {
                        //Si showPassword es true cambia a false y viceversa
                        //showPassword = !showPassword
                        viewModel.togglePasswordVisibility()
                    }) {
                        //Seleccionamos iconos diferentes para mostrar u ocultar
                        // contraseña
                        //if (showPassword) {
                        Icon(
                            if (viewModel.showPassword)
                                Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Mostrar contraseña"
                        )
                    }
                }
            )


                //value = textPassword,
                //onValueChange = { newValue: String -> textPassword = newValue },
                //Mostramos texto visible u oculto según estado de showPassword
                //visualTransformation =
                  //  if (showPassword) VisualTransformation.None
                    //else PasswordVisualTransformation().
            IconButton(onClick = {
                viewModel.login {
                    navController.navigate(Screens.ListaContactos.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                }
            }){
                Icon(Icons.Default.Bungalow, contentDescription = "Login")
            }

            HorizontalDivider()
        }
    }
}