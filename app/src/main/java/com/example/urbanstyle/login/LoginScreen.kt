package com.example.urbanstyle.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.urbanstyle.R // Asegúrate de importar tu R
import com.example.urbanstyle.ui.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    // Usamos el viewModel que definimos en AppViewModels.kt
    vm: LoginViewModel = viewModel()
) {
    // Observamos el estado del ViewModel (Flow)
    val state by vm.uiState.collectAsState()

    // Efecto para navegar cuando el login es exitoso
    LaunchedEffect(state.loginExitoso) {
        if (state.loginExitoso) {
            // Navegar a la pantalla principal (Home)
            // Borramos el stack para que no pueda volver al login con "atrás"
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
            // Opcional: Resetear estado
            vm.resetState()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // --- LOGO Y TÍTULO (Manteniendo tu estética) ---
            Image(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de tener logo.jpg/png en res/drawable
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bienvenido",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- CAMPOS DE TEXTO ---

            // Campo Correo
            OutlinedTextField(
                value = state.correo,
                onValueChange = { vm.onCorreoChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = state.errorLogin != null
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = state.contrasena,
                onValueChange = { vm.onPasswordChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(), // Ocultar caracteres
                isError = state.errorLogin != null
            )

            // Mensaje de Error
            if (state.errorLogin != null) {
                Text(
                    text = state.errorLogin ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- BOTÓN DE INGRESO ---
            Button(
                onClick = { vm.iniciarSesion() },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Verificando...")
                } else {
                    Text("Iniciar Sesión", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- ENLACE A REGISTRO ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "¿No tienes cuenta? ")
                Text(
                    text = "Regístrate aquí",
                    modifier = Modifier.clickable {
                        navController.navigate("registro")
                    },
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Previsualización para Android Studio
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    // Nota: El ViewModel requiere Application context, por lo que el preview
    // podría fallar si no se moquea, pero para diseño visual básico sirve.
    val navController = rememberNavController()
    // LoginScreen(navController = navController) // Comentado para evitar error de contexto en preview
}