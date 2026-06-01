package com.example.agendacontactosgrhr.data.repository

import com.example.agendacontactosgrhr.data.local.SessionManager
import com.example.agendacontactosgrhr.data.remote.datasource.ApiService
import com.example.agendacontactosgrhr.data.remote.model.LoginRequest
import com.example.agendacontactosgrhr.data.remote.model.UsuarioDto
import javax.inject.Inject
import javax.inject.Singleton

// Repositorio que gestiona el token JWT
@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {

    suspend fun login(email: String, password: String): Result<UsuarioDto> {
        return try {
            val response = apiService.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    sessionManager.guardarToken(body.token)
                    sessionManager.guardarNombre(body.usuario.nombre)
                    sessionManager.guardarEmail(body.usuario.email)

                    return Result.success(body.usuario)
                } else {
                    return Result.failure(Exception("Respuesta vacia de la API"))
                }
            } else {
                return Result.failure(Exception("Credenciales incorrectas"))
            }

        } catch (e: Exception) {
            return Result.failure(Exception("No se pudo conectar con el servidor"))
        }
    }

    // Cierra sesion borrando token y datos guardados
    fun cerrarSesion() {
        sessionManager.cerrarSesion()
    }
}