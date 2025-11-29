package com.example.urbanstyle.ui.screens

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.urbanstyle.login.LoginScreen
import com.example.urbanstyle.ui.login.LoginViewModel
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    //Usar Pixel 5 API 34 para el buen funcionamiento de los testing
    // Regla necesaria para interactuar con Compose
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_camposExisten_y_recibenTexto() {
        // Configuración inicial de la pantalla
        composeTestRule.setContent {
            // Creamos un NavController de prueba
            val navController = TestNavHostController(LocalContext.current)
            // Creamos el ViewModel real para probar la lógica junto con la UI
            val viewModel = LoginViewModel()

            LoginScreen(navController = navController, vm = viewModel)
        }

        composeTestRule.onNodeWithText("Usuario")
            .assertExists()
            .performTextInput("Admin")

        composeTestRule.onNodeWithText("Contraseña")
            .assertExists()
            .performTextInput("Hola")

        // Verificar visualmente que el texto se escribió (opcional pero útil)
        composeTestRule.onNodeWithText("Admin").assertIsDisplayed()
    }

    @Test
    fun loginScreen_credencialesIncorrectas_muestraError() {
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            val viewModel = LoginViewModel()
            LoginScreen(navController = navController, vm = viewModel)
        }

        // 1. Ingresar datos incorrectos
        composeTestRule.onNodeWithText("Usuario").performTextInput("UsuarioFalso")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123Mal")

        // 2. Hacer click en el botón "Iniciar Sesión"
        // Buscamos el botón por el texto que contiene
        composeTestRule.onNodeWithText("Iniciar Sesión").performClick()

        // 3. Verificar que aparece el mensaje de error "Credenciales Invalidas"
        // Esperamos hasta que el árbol de UI se actualice
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Credenciales Invalidas")
            .assertExists()
            .assertIsDisplayed()
    }

}