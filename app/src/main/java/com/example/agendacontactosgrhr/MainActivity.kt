package com.example.agendacontactosgrhr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.agendacontactosgrhr.navigation.AppNavigation
import com.example.agendacontactosgrhr.ui.theme.AgendaContactosGRHRTheme
import dagger.hilt.android.AndroidEntryPoint

// MainActivity: punto de entrada de la app
//@AndroidEntryPoint esd necesario para usar Hilt (inyección de dependencias)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Nuestro tema personalizado
            AgendaContactosGRHRTheme {
                //inicio de navegación de la app
                AppNavigation()
            }
        }
    }
}