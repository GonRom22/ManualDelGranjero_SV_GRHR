package com.example.agendacontactosgrhr.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Después de investigar descubrimos que hay que importar en el manifest las siguientes lineas:
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
class NetworkMonitor @Inject constructor(context: Context) {
    // Servicio del sistema para gestionar la red
    @ApplicationContext private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected: Flow<Boolean> = callbackFlow {
        // 1. Definimos el objeto Callback que se llama cuando hay una conexión
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true) // Hay conexión
            }

            override fun onLost(network: Network) {
                trySend(false) // No hay conexión
            }
        }

        // 2. Configuramos la petición para detectar internet
        // OJO: Fíjate bien en los paréntesis aquí
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        // 3. Registramos el callback en el sistema
        connectivityManager.registerNetworkCallback(request, callback)

        // 4. Comprobamos el estado inicial nada más empezar
        val estadoActual = connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false

        trySend(estadoActual)

        // 5. Cierre de seguridad (MUY IMPORTANTE)
        // Esto evita que la app siga gastando batería cuando se cierra
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged() // Si el estado no cambia (ej. true -> true), no emite nada
}