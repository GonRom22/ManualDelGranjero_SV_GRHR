package com.example.agendacontactosgrhr.viewmodel

import com.example.agendacontactosgrhr.data.remote.model.UsuarioDto
import com.example.agendacontactosgrhr.data.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

  private val testDispatcher = StandardTestDispatcher()
  private lateinit var authRepository: AuthRepository
  private lateinit var viewModel: LoginViewModel

  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
    authRepository = mockk()
    viewModel = LoginViewModel(authRepository)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  //Login con credenciales validas navega a HomeScreen
  @Test
  fun loginConCredencialesValidasNavegaAHomeScreen() = runTest {
    val usuario = UsuarioDto(id = 1, nombre = "Hector", email =
      "hector@test.com")
    coEvery { authRepository.login("hector@test.com", "1234567890") } returns
            Result.success(usuario)

    viewModel.onEmailChange("hector@test.com")
    viewModel.onPasswordChange("1234567890")

    var navegado = false
    viewModel.login { navegado = true }
    advanceUntilIdle()

    assertTrue("Se dirige a homeScreen tras login exitoso", navegado)
    assertNull("No deberia haber error", viewModel.errorMessage)
    assertFalse("No deberia cargar", viewModel.isLoading)
  }

  //Credenciales invalidas
  @Test
  fun login_conCredencialesInvalidas_muestraError() = runTest {
    coEvery { authRepository.login("otro@test.com", "otro") } returns
            Result.failure(Exception("Credenciales incorrectas"))

    viewModel.onEmailChange("otro@test.com")
    viewModel.onPasswordChange("otro")

    var navegado = false
    viewModel.login { navegado = true }
    advanceUntilIdle()

    assertFalse("No deberia navegar con credenciales invalidas", navegado)
    assertNotNull("Deberia mostrar mensaje de error", viewModel.errorMessage)
    assertEquals("Credenciales incorrectas", viewModel.errorMessage)
    assertFalse("No deberia estar cargando", viewModel.isLoading)
  }

  @Test
  fun login_conCredencialesVacias_muestraError() = runTest {
    var navegado = false
    viewModel.login { navegado = true }
    advanceUntilIdle()

    assertFalse("No deberia navegar con credenciales vacias", navegado)
    assertEquals("Rellena todos los campos", viewModel.errorMessage)
  }
}
