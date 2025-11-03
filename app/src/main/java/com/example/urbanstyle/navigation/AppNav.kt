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
import com.example.urbanstyle.ui.BlogScreen
import com.example.urbanstyle.ui.BlogDetalleScreen
import com.example.urbanstyle.ui.NosotrosScreen
import com.example.urbanstyle.data.repository.ProductoRepository
import com.example.urbanstyle.data.repository.BlogRepository
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

object Rutas {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PRODUCTOS = "productos"

    const val BLOG = "blog"                // Lista
    const val BLOG_DETALLE = "blog/{id}"   // Detalle

    const val NOSOTROS = "nosotros"
    const val CARRITO = "carrito"

    const val PRODUCTO_DETALLE = "producto/{codigo}"
    const val ARG_CODIGO = "codigo"
}

@Composable
fun AppNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Rutas.HOME
    ) {
        // ---------- LOGIN ----------
        composable(Rutas.LOGIN) { LoginScreen(navController = navController) }

        // ---------- HOME ----------
        composable(Rutas.HOME) { HomeScreen(navController = navController) }

        // ---------- PRODUCTOS ----------
        composable(Rutas.PRODUCTOS) { ProductosScreen(navController = navController) }

        // ---------- BLOG ----------
        composable(Rutas.BLOG) { BlogScreen(navController = navController) }

        // ---------- BLOG DETALLE ----------
        composable(
            route = Rutas.BLOG_DETALLE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("id") ?: -1
            val blogRepo = BlogRepository()
            val post = blogRepo.porId(id)
            BlogDetalleScreen(
                navController = navController,
                post = post
            )
        }

        // ---------- PRODUCTO DETALLE ----------
        composable(
            route = Rutas.PRODUCTO_DETALLE,
            arguments = listOf(navArgument(Rutas.ARG_CODIGO) { type = NavType.StringType })
        ) { backStack ->
            val codigo = backStack.arguments?.getString(Rutas.ARG_CODIGO).orEmpty()
            val prodRepo = ProductoRepository()
            val producto = runCatching { prodRepo.porCodigo(codigo) }.getOrNull()
            ProductoDetalleScreen(
                navController = navController,
                producto = producto,
                codigo = codigo
            )
        }

        // ---------- NOSOTROS ----------
        composable(Rutas.NOSOTROS) { NosotrosScreen(navController = navController) }

        // ---------- CARRITO (placeholder) ----------
        composable(Rutas.CARRITO) {
            androidx.compose.material3.Scaffold { inner ->
                androidx.compose.material3.Text(
                    text = "Carrito en desarrollo 🛒",
                    modifier = androidx.compose.ui.Modifier
                        .padding(inner)
                        .padding(24.dp)
                )
            }
        }
    }
}
