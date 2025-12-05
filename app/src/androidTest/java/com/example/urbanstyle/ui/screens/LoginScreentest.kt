package com.example.urbanstyle.ui.screens

import android.app.Application
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider

import com.example.urbanstyle.login.LoginScreen
import com.example.urbanstyle.ui.login.LoginViewModel

import org.junit.Rule
import org.junit.Test


class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_camposExisten_y_recibenTexto() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()

        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            val viewModel = LoginViewModel(appContext)

            LoginScreen(navController = navController, vm = viewModel)
        }

        // Campo "Correo Electrónico"
        composeTestRule.onNode(
            hasText("Correo Electrónico") and hasSetTextAction()
        )
            .assertExists()
            .performTextInput("admin@milsabores.cl")

        // Campo "Contraseña"
        composeTestRule.onNode(
            hasText("Contraseña") and hasSetTextAction()
        )
            .assertExists()
            .performTextInput("Hola123")

        // Verificamos que la pantalla sigue visible (no crasheó)
        composeTestRule.onNodeWithText("Iniciar Sesión")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun loginScreen_sinDatos_muestraErrorDeCamposObligatorios() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()

        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            val viewModel = LoginViewModel(appContext)

            LoginScreen(navController = navController, vm = viewModel)
        }

        // Sin escribir nada, apretamos el botón
        composeTestRule.onNodeWithText("Iniciar Sesión")
            .assertExists()
            .performClick()

        // Esta rama del ViewModel NO usa corrutinas (es sincrónica),
        // así que el mensaje "Ingresa correo y contraseña" ya está seteado.
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Ingresa correo y contraseña")
            .assertExists()
            .assertIsDisplayed()
    }
}
