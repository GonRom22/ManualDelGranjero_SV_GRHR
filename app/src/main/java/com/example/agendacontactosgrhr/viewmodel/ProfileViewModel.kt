package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    // Nombre del usuario logueado
    val userName: StateFlow<String?> = sessionManager.nombreUsuario
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            sessionManager.obtenerNombre()
        )

    // Email del usuario logueado
    val userEmail: StateFlow<String?> = sessionManager.emailUsuario
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            sessionManager.obtenerEmail()
        )

    fun logout(onLogout: () -> Unit) {
        sessionManager.cerrarSesion()
        onLogout()
    }
}
