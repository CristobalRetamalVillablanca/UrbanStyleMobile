package com.example.urbanstyle.ui.screens

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.urbanstyle.ui.registro.RegistroScreen
import com.example.urbanstyle.viewmodel.RegistroViewModel
import org.junit.Rule
import org.junit.Test

class RegistroScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Guardamos una referencia al último ViewModel usado en la UI
    private lateinit var ultimoViewModel: RegistroViewModel

    private fun setRegistroContent() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()

        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            val viewModel = RegistroViewModel(appContext)
            ultimoViewModel = viewModel

            RegistroScreen(
                navController = navController,
                registroViewModel = viewModel
            )
        }
    }

    @Test
    fun elementosIniciales_seMuestranCorrectamente() {
        setRegistroContent()

        // Título del formulario
        composeTestRule.onNodeWithText("Formulario de Registro")
            .assertExists()

        // Campos principales (solo que existan)
        composeTestRule.onNode(
            hasText("Nombre Completo") and hasSetTextAction()
        ).assertExists()

        composeTestRule.onNodeWithText("Fecha de Nacimiento")
            .assertExists()

        composeTestRule.onNode(
            hasText("Correo Electrónico") and hasSetTextAction()
        ).assertExists()

        composeTestRule.onNode(
            hasText("Contraseña") and hasSetTextAction()
        ).assertExists()

        composeTestRule.onNode(
            hasText("Confirmar Contraseña") and hasSetTextAction()
        ).assertExists()

        composeTestRule.onNode(
            hasText("Teléfono (Opcional)") and hasSetTextAction()
        ).assertExists()

        composeTestRule.onNode(
            hasText("Código de Descuento (Opcional)") and hasSetTextAction()
        ).assertExists()

        // Botón de registro
        composeTestRule.onNodeWithText("Registrarse")
            .assertExists()
    }

    @Test
    fun interaccionUsuario_llenaCampos_sinErroresVisuales() {
        setRegistroContent()

        // Llenar nombre
        composeTestRule.onNode(
            hasText("Nombre Completo") and hasSetTextAction()
        ).performTextInput("Cristóbal Pérez")

        // Llenar correo
        composeTestRule.onNode(
            hasText("Correo Electrónico") and hasSetTextAction()
        ).performTextInput("cristobal@milsabores.cl")

        // Llenar contraseñas
        composeTestRule.onNode(
            hasText("Contraseña") and hasSetTextAction()
        ).performTextInput("Clave123")

        composeTestRule.onNode(
            hasText("Confirmar Contraseña") and hasSetTextAction()
        ).performTextInput("Clave123")

        // Teléfono
        composeTestRule.onNode(
            hasText("Teléfono (Opcional)") and hasSetTextAction()
        ).performTextInput("987654321")

        // Código descuento
        composeTestRule.onNode(
            hasText("Código de Descuento (Opcional)") and hasSetTextAction()
        ).performTextInput("DESC10")

        // Ver que el botón sigue existiendo (pantalla viva)
        composeTestRule.onNodeWithText("Registrarse")
            .assertExists()
    }

    @Test
    fun registroConNombreMuyCorto_seteaErrorNombreEnViewModel() {
        setRegistroContent()

        // Nombre demasiado corto -> gatilla errorNombre en el ViewModel
        composeTestRule.onNode(
            hasText("Nombre Completo") and hasSetTextAction()
        ).performTextInput("Ana") // < 4 caracteres

        // Rellenamos otros campos para evitar que fallen otras validaciones
        composeTestRule.onNode(
            hasText("Correo Electrónico") and hasSetTextAction()
        ).performTextInput("ana@milsabores.cl")

        composeTestRule.onNode(
            hasText("Contraseña") and hasSetTextAction()
        ).performTextInput("Clave123")

        composeTestRule.onNode(
            hasText("Confirmar Contraseña") and hasSetTextAction()
        ).performTextInput("Clave123")

        // Click en "Registrarse"
        composeTestRule.onNodeWithText("Registrarse")
            .performClick()

        // Ahora esperamos a que Compose quede idle y LEEMOS el estado dentro de runOnIdle
        composeTestRule.runOnIdle {
            val state = ultimoViewModel.uiState.value
            val errorNombre = state.errorNombre
            assert(errorNombre == "Nombre muy corto") {
                "Se esperaba errorNombre = 'Nombre muy corto' pero fue: $errorNombre"
            }
        }
    }

}
