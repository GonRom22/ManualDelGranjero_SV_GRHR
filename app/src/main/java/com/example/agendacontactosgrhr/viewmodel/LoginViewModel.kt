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

//ViewModel de la pantalla de Login. Gestiona el estado del formulario y llama a la API
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    //Mensaje de error a mostrar en pantalla (null = sin error)
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newValue: String) {
        email = newValue
        errorMessage = null
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        errorMessage = null
    }

    fun togglePasswordVisibility() {
        showPassword = !showPassword
    }

    //Envia las credenciales a la API. Si son correctas ejecuta onSuccess (navega a Home)
    fun login(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Rellena todos los campos"
            return
        }

        viewModelScope.launch {
            isLoading = true
            authRepository.login(email, password).fold(
                onSuccess = {
                    isLoading = false
                    onSuccess()
                },
                onFailure = { error ->
                    isLoading = false
                    errorMessage = error.message ?: "Error desconocido"
                })
        } }
}









