package com.example.agendacontactosgrhr.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.Gray),
                title = { Text("Login Screen",
                    color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home")
                    }) {
                        Icon(
                            Icons.Default.Chalet,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            HorizontalDivider()
            //Texto de Usuario
            var textName by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.padding(16.dp),
                label = { Text("Introduce tu nombre") },
                value = textName,
                onValueChange = { newValue: String -> textName = newValue },
                singleLine = true
            )

            //Contraseña
            var textPassword by rememberSaveable { mutableStateOf("") }

            //Variable que controla si mostramos u ocultamos contraseña
            var showPassword by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                trailingIcon = {
                    IconButton(onClick = {
                        //Si showPassword es true cambia a false y viceversa
                        showPassword = !showPassword
                    }) {
                        //Seleccionamos iconos diferentes para mostrar u ocultar
                        // contraseña
                        if (showPassword) {
                            Icon(
                                Icons.Default.Visibility,
                                contentDescription = "Contraseña visible"
                            )
                        } else
                            Icon(
                                Icons.Default.VisibilityOff,
                                "Contraseña oculta"
                            )
                    }
                },
                modifier = Modifier.padding(16.dp),
                label = { Text("Password") },
                value = textPassword,
                onValueChange = { newValue: String -> textPassword = newValue },
                singleLine = true,
                //Mostramos texto visible u oculto según estado de showPassword
                visualTransformation =
                    if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation()
            )

            HorizontalDivider()
        }
    }
}