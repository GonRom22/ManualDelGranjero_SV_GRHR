package com.example.agendacontactosgrhr.data.remote.model
//Creamos este archivo al configurar el JWT

data class LoginRequest( //Aqui la api espera un JSON con email y contraseña
    val email: String,
    val password: String
)
data class LoginResponse(//Aqui la api devuelve un JSON con el token y el usuario
    val token: String,
    val usuario: UsuarioDto
)
data class UsuarioDto(//Datos del usuario devueltos por la api
    val id: Int,
    val nombre: String,
    val email: String
)





















