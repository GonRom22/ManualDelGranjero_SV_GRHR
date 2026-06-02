package com.example.agendacontactosgrhr.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendacontactosgrhr.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel de la pantalla de Registro. Gestiona el formulario y llama a la API
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var nombre by mutableStateOf("")          // opcional
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newValue: String) { email = newValue; errorMessage = null }
    fun onNombreChange(newValue: String) { nombre = newValue; errorMessage = null }
    fun onPasswordChange(newValue: String) { password = newValue; errorMessage = null }
    fun onConfirmPasswordChange(newValue: String) { confirmPassword = newValue; errorMessage = null }
    fun togglePasswordVisibility() { showPassword = !showPassword }

    //Valida el formulario y registra. Si va bien ejecuta onSuccess (navega a Home)
    fun register(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Rellena todos los campos"
            return
        }
        if (password != confirmPassword) {
            errorMessage = "Las contraseñas no coinciden"
            return
        }

        viewModelScope.launch {
            isLoading = true
            authRepository.register(
                email = email,
                password = password,
                nombre = nombre.ifBlank { null }   // si está vacío, mandamos null
            ).fold(
                onSuccess = {
                    isLoading = false
                    onSuccess()
                },
                onFailure = { error ->
                    isLoading = false
                    errorMessage = error.message ?: "Error desconocido"
                }
            )
        }
    }
}
