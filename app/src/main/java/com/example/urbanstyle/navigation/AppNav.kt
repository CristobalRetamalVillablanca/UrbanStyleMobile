package com.example.urbanstyle.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.urbanstyle.login.LoginScreen
import com.example.urbanstyle.ui.HomeScreen
import com.example.urbanstyle.ui.ProductosScreen
import com.example.urbanstyle.ui.ProductoDetalleScreen
import com.example.urbanstyle.data.repository.ProductoRepository
import com.example.urbanstyle.navigation.Rutas

object Rutas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PRODUCTOS = "productos"
    const val BLOG = "blog"
    const val NOSOTROS = "nosotros"
    const val CARRITO = "carrito"

    const val PRODUCTO_DETALLE = "producto/{codigo}"
    const val ARG_CODIGO = "codigo"
}

@Composable
fun AppNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {
        composable(Rutas.LOGIN) { LoginScreen(navController = navController) }
        composable(Rutas.HOME) { HomeScreen(navController = navController) }
        composable(Rutas.PRODUCTOS) { ProductosScreen(navController = navController) }

        // Detalle
        composable(
            route = Rutas.PRODUCTO_DETALLE,
            arguments = listOf(navArgument(Rutas.ARG_CODIGO) { type = NavType.StringType })
        ) { backStack ->
            val codigo = backStack.arguments?.getString(Rutas.ARG_CODIGO).orEmpty()
            val repo = ProductoRepository()
            val producto = runCatching { repo.porCodigo(codigo) }.getOrNull()
            ProductoDetalleScreen(
                navController = navController,
                producto = producto,
                codigo = codigo
            )
        }
    }
}
