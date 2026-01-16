package com.example.agendacontactosgrhr

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * MyApplication es la primera clase que se ejecuta cuando la app arranca.
 * Aquí podemos hacer configuraciones globales que necesitamos antes que cualquier otra cosa,
 * como inicializar Hilt (para inyección de dependencias), Firebase, Room, o variables globales.
 *
 * @HiltAndroidApp le dice a Hilt: “Esta app va a usar inyección de dependencias,
 * prepárate desde el inicio y crea un contenedor global que dure toda la vida de la app”.
 *
 * Gracias a esto, luego Hilt puede inyectar repositorios, ViewModels o cualquier dependencia
 * directamente en Activities, Fragments o Composables.
 */

@HiltAndroidApp
class MyApplication : Application()