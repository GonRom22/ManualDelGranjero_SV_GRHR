package com.example.agendacontactosgrhr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.agendacontactosgrhr.ui.theme.AgendaContactosGRHRTheme
import com.example.agendacontactosgrhr.view.NavigatorHostController
import dagger.hilt.android.AndroidEntryPoint

//Main
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendaContactosGRHRTheme {
                NavigatorHostController()
            }
        }
    }
}