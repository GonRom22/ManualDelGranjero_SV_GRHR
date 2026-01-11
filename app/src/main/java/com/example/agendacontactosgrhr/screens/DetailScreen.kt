package com.example.agendacontactosgrhr.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, nombre : String?) {
    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.LightGray),
                title = {
                    Text("Detail Screen",
                        color = Color.White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()//Vuelve a la pantalla anterior
                        }
                    ){
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Volver",
                            tint = Color.White)
                    }
                }
            )
        }
    ){
            paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            Row(modifier = Modifier.padding(16.dp)){
                Text("Nombre:  $nombre",
                    fontSize = 24.sp,
                    color = Color.Black)//Aquí se muestra el nombre que recibe.
            }
        }
    }
}