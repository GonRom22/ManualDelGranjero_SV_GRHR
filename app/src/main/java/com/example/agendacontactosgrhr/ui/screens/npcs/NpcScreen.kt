package com.example.agendacontactosgrhr.ui.screens.npcs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendacontactosgrhr.navigation.Screens
import com.example.agendacontactosgrhr.ui.screens.PantallaBase
import com.example.agendacontactosgrhr.viewmodel.ContactosViewModel

@Composable
fun NpcScreen(navController: NavHostController) {

    val viewModel: ContactosViewModel = hiltViewModel()
    val npcs by viewModel.contactos.collectAsState()

    PantallaBase(
        titulo = "NPCs",
        navController = navController
    ) { padding ->

        LazyColumn(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            items(npcs) { npc ->

                Card(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("${Screens.DetailNpc.route}/${npc.id}")
                        }
                ) {
                    Column(modifier = androidx.compose.ui.Modifier.padding(16.dp)) {
                        Text(npc.name)
                        Text(npc.ubicacion)
                    }
                }
            }
        }
    }
}
