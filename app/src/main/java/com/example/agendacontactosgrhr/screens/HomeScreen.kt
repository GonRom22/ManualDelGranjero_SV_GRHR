package com.example.agendacontactosgrhr.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//Le pasamos el controlador de navegación y el view model entre ().
fun HomeScreen(navController: NavHostController,
               viewModel: ContactosViewModel = viewModel()
) {
    val contactos = viewModel.contactos

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.Gray),
                title = {
                    Text("Home",
                        color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ){
            paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            contactos.forEach { contacto ->
                ItemContact(
                    name = contacto.nombre,
                    phone = contacto.telefono,
                    navController = navController
                )
            }
        }
    }

}