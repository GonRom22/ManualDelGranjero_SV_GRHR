package com.example.agendacontactosgrhr.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
//View model de Login
class LoginViewModel : ViewModel(){

    var userName by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var showPassword by mutableStateOf(false)
        private set

    fun onUserNameChange(newValue: String){
        userName = newValue
    }

    fun onPasswordChange(newValue: String){
        password = newValue
    }

    fun togglePasswordVisibility(){
        showPassword = !showPassword
    }

    fun login(onSuccess: () -> Unit){
        if (userName.isNotBlank() && password.isNotBlank()){
            onSuccess()
        }
    }
}