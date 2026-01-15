package com.example.agendacontactosgrhr.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel





@OptIn(ExperimentalMaterial3Api::class)
@Composable
//Le pasamos el controlador de navegación y el view model entre ().
fun HomeScreen(
    navController: NavHostController,
    viewModel: ContactosViewModel = hiltViewModel(),//Hilt inyecta automáticamente el RepositorioCOntactos dentro del ViewModel
    onContactoClick: (String) -> Unit
) {
    //Con collectAsState() -> recolectamos el flujo de contactos como un estado para que el
    // @Composable se reactive automáticamente al cambiar de datos/estado
    val contactos by viewModel.contactosFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.Gray),
                title = {
                    Text("Agenda de Contactos",
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
        //Aquí indicamos dónde se dibuja el Snackbar

    ){  paddingValues ->
        if (contactos.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center){
                CircularProgressIndicator()
            }
        }else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(contactos){contacto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                coroutineScope.launch {
                                    viewModel.onContactoSeleccionado(contacto.nombre)
                                }
                                onContactoClick(contacto.telefono)
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ){
                        Text(
                            text = contacto.nombre,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
        /*
        Column(modifier = Modifier.padding(paddingValues)) {
            contactos.forEach { contacto ->
                ItemContact(
                    name = contacto.nombre,
                    phone = contacto.telefono,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    //Avisamos al ViewModel de que se ha pulsado un contacto
                    onClick = {
                        viewModel.onContactoSeleccionado(contacto.nombre)
                    }
                )
            }
        }*/
    }
}