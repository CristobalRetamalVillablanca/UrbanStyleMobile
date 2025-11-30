package com.example.urbanstyle.ui.screens


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.example.urbanstyle.ui.registro.RegistroScreen
import com.example.urbanstyle.ui.registro.RegistroUiState
import com.example.urbanstyle.viewmodel.RegistroViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegistroScreenTest {

    // Regla para iniciar el entorno de Compose
    @get:Rule
    val composeTestRule = createComposeRule()

    // --- MOCKS ---
    // Creamos las versiones "falsas" de las clases.
    // Al ser el ViewModel 'open', Mockito ya no fallará aquí.
    private val mockNavController = mock(NavController::class.java)
    private val mockViewModel = mock(RegistroViewModel::class.java)

    // Simulamos los flujos de datos (StateFlow)
    // Usamos MutableStateFlow para poder cambiar los valores durante el test si es necesario
    private val fakeUiStateFlow = MutableStateFlow(RegistroUiState
        ())
    private val fakeComunasFlow = MutableStateFlow(listOf("Santiago", "Providencia"))
    // Las regiones son una lista simple en tu ViewModel, no un Flow
    private val fakeRegionesList = listOf("Metropolitana", "Valparaiso")

    @Test
    fun elementosIniciales_seMuestranCorrectamente() {
        // 1. GIVEN (Preparación)
        // Configuramos el mock para que devuelva datos vacíos/iniciales
        whenever(mockViewModel.uiState).thenReturn(fakeUiStateFlow)
        whenever(mockViewModel.comunas).thenReturn(fakeComunasFlow)
        whenever(mockViewModel.regiones).thenReturn(fakeRegionesList)

        // 2. WHEN (Carga)
        composeTestRule.setContent {
            RegistroScreen(
                navController = mockNavController,
                registroViewModel = mockViewModel
            )
        }

        // 3. THEN (Verificación)
        // Verificamos elementos estáticos y campos vacíos
        composeTestRule.onNodeWithText("Formulario de Registro").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre Completo").assertIsDisplayed()

        // Elementos de más abajo: Agregamos performScrollTo() por seguridad
        composeTestRule.onNodeWithText("Correo Electrónico")
            .performScrollTo() // <--- MAGIA: Baja la pantalla si es necesario
            .assertIsDisplayed()

        // El botón del final (definitivamente necesita scroll)
        composeTestRule.onNodeWithText("Registrarse")
            .performScrollTo() // <--- MAGIA: Baja hasta el final
            .assertIsDisplayed()
    }

    @Test
    fun mostrarError_cuandoElEstadoTieneError() {
        // 1. GIVEN
        // Creamos un estado que simula un error en el nombre
        val estadoConError = RegistroUiState(
            nombreCompleto = "A",
            errorNombre = "El nombre es muy corto"
        )
        // Actualizamos el flujo falso con este estado
        fakeUiStateFlow.value = estadoConError

        whenever(mockViewModel.uiState).thenReturn(fakeUiStateFlow)
        whenever(mockViewModel.comunas).thenReturn(fakeComunasFlow)
        whenever(mockViewModel.regiones).thenReturn(fakeRegionesList)

        // 2. WHEN
        composeTestRule.setContent {
            RegistroScreen(navController = mockNavController, registroViewModel = mockViewModel)
        }

        // 3. THEN
        // El texto del error debe aparecer en pantalla
        composeTestRule.onNodeWithText("El nombre es muy corto").assertIsDisplayed()
    }

    @Test
    fun interaccionUsuario_llamaAlViewModel() {
        // 1. GIVEN
        whenever(mockViewModel.uiState).thenReturn(fakeUiStateFlow)
        whenever(mockViewModel.comunas).thenReturn(fakeComunasFlow)
        whenever(mockViewModel.regiones).thenReturn(fakeRegionesList)

        composeTestRule.setContent {
            RegistroScreen(navController = mockNavController, registroViewModel = mockViewModel)
        }

        // 2. WHEN
        // El usuario escribe en el input de Correo
        composeTestRule.onNodeWithText("Correo Electrónico")
            .performTextInput("alumno@duocuc.cl")

        // 3. THEN
        // Verificamos que la función 'onCorreoChange' del ViewModel fue llamada.
        // ESTO FUNCIONA PORQUE LA FUNCIÓN AHORA ES 'OPEN'
        verify(mockViewModel).onCorreoChange("alumno@duocuc.cl")
    }

    @Test
    fun registroExitoso_navegaHaciaAtras() {
        // 1. GIVEN
        // Simulamos que el registro fue exitoso
        val estadoExito = RegistroUiState(registroExitoso = true)
        fakeUiStateFlow.value = estadoExito

        whenever(mockViewModel.uiState).thenReturn(fakeUiStateFlow)
        whenever(mockViewModel.comunas).thenReturn(fakeComunasFlow)
        whenever(mockViewModel.regiones).thenReturn(fakeRegionesList)

        // 2. WHEN
        composeTestRule.setContent {
            RegistroScreen(navController = mockNavController, registroViewModel = mockViewModel)
        }

        // 3. THEN
        // La pantalla debió haber ordenado al NavController volver atrás
        verify(mockNavController).popBackStack()
    }
}