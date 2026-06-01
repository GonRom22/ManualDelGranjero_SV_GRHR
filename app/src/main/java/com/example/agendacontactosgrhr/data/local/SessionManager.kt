package com.example.agendacontactosgrhr.data.local
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
)
{

    //Crea las SharedPreferences cifradas con una clave maestra del Android Keystore
    private val prefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "stardew_session",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,

            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    //Flow observable del nombre
    private val _nombreUsuario = MutableStateFlow<String?>(null)
    val nombreUsuario: Flow<String?> = _nombreUsuario

    //Flow observable del email
    private val _emailUsuario = MutableStateFlow<String?>(null)
    val emailUsuario: Flow<String?> = _emailUsuario

    //Carga los datos guardados en las SharedPreferences cifradas al iniciar
    init {
        _nombreUsuario.value = prefs.getString("user_name", null)
        _emailUsuario.value = prefs.getString("user_email", null)
    }

    //Guarda el token JWT recibido del servidor tras un login correcto
    fun guardarToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    //Recupera el token JWT. Devuelve null si no hay sesion activa
    fun obtenerToken(): String? = prefs.getString("jwt_token", null)
    fun guardarNombre(name: String) {
        prefs.edit().putString("user_name", name).apply()
        _nombreUsuario.value = name
    }

    fun obtenerNombre(): String? = prefs.getString("user_name", null)
    fun guardarEmail(email: String) {
        prefs.edit().putString("user_email", email).apply()
        _emailUsuario.value = email
    }

    fun obtenerEmail(): String? = prefs.getString("user_email", null)
    fun cerrarSesion() {
        prefs.edit().clear().apply()
        _nombreUsuario.value = null
        _emailUsuario.value = null
    }
    //Comprueba si hay sesion activa (si existe un token guardado)
    fun sesionActivaOk(): Boolean = obtenerToken() != null
}









