package com.example.agendacontactosgrhr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    val userName: StateFlow<String?> = sessionManager.nombreUsuario
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
            null)
    fun logout(onLogout: () -> Unit) {
        sessionManager.cerrarSesion()
        onLogout()
    }
    }
