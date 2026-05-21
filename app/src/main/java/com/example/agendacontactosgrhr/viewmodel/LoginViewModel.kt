package com.example.agendacontactosgrhr.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    var userName by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set

    fun onUserNameChange(newValue: String) {
        userName = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun togglePasswordVisibility() {
        showPassword = !showPassword
    }

    fun login(onSuccess: () -> Unit) {
        if (userName.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch {
                sessionManager.saveSession(userName)
                onSuccess()
            }
        }
    }
}
