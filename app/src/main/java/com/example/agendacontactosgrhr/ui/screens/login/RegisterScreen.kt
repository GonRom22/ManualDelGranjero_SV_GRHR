package com.example.agendacontactosgrhr.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.agendacontactosgrhr.viewmodel.RegisterViewModel

/**
 * Pantalla de Registro
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Crear cuenta",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = viewModel::onNombreChange,
                modifier = Modifier.padding(16.dp),
                label = { Text("Nombre (opcional)",
                    fontSize = 24.sp) },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.padding(16.dp),
                label = { Text("Email",
                    fontSize = 24.sp) },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Password",
                    fontSize = 24.sp) },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                visualTransformation =
                    if (viewModel.showPassword) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            if (viewModel.showPassword) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Mostrar contraseña"
                        )
                    }
                }
            )

            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text("Confirmar password",
                    fontSize = 24.sp) },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                visualTransformation =
                    if (viewModel.showPassword) VisualTransformation.None
                    else PasswordVisualTransformation()
            )

            viewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.btn_login),
                contentDescription = "Registrarse",
                modifier = Modifier
                    .padding(16.dp)
                    .width(270.dp)
                    .height(90.dp)
                    .clickable {
                        viewModel.register {
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(Screens.Login.route) { inclusive = true }
                            }
                        }
                    }
            )

            Text(
                "¿Ya tienes cuenta? Inicia sesión",
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.popBackStack() }
            )
        }
    }
}
