package com.example.urbanstyle.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.urbanstyle.login.LoginScreen
import com.example.urbanstyle.ui.components.BottomBar

object Rutas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PRODUCTOS = "productos"
    const val BLOG = "blog"
    const val NOSOTROS = "nosotros"
    const val CARRITO = "carrito"
}

@Composable
fun AppNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN   // Arranca en Login como pediste
    ) {
        composable(Rutas.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(Rutas.HOME) {
            PageScaffold(titulo = "Inicio", navController = navController)
        }
        composable(Rutas.PRODUCTOS) {
            PageScaffold(titulo = "Productos", navController = navController)
        }
        composable(Rutas.BLOG) {
            PageScaffold(titulo = "Blog", navController = navController)
        }
        composable(Rutas.NOSOTROS) {
            PageScaffold(titulo = "Nosotros", navController = navController)
        }
        composable(Rutas.CARRITO) {
            PageScaffold(titulo = "Carrito (en desarrollo 🛒)", navController = navController)
        }
    }
}

@Composable
private fun PageScaffold(
    titulo: String,
    navController: NavHostController
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { inner ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
