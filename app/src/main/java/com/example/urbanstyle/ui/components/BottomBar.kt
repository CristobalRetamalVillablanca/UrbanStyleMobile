package com.example.urbanstyle.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.urbanstyle.navigation.Rutas

private val CafeOscuro = Color(0xFF5D4037) // café oscuro
private val Blanco     = Color(0xFFFFFFFF)

data class NavItem(
    val ruta: String,
    val etiqueta: String,
    val icono: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        NavItem(Rutas.HOME, "Inicio", Icons.Filled.Home),
        NavItem(Rutas.PRODUCTOS, "Productos", Icons.Filled.Cake),
        NavItem(Rutas.BLOG, "Blog", Icons.Filled.Article),
        NavItem(Rutas.NOSOTROS, "Nosotros", Icons.Filled.Info),
        NavItem(Rutas.LOGIN, "Login", Icons.Filled.Lock),
        NavItem(Rutas.CARRITO, "Carrito", Icons.Filled.ShoppingCart),
        NavItem(Rutas.CAMERA, "Escanear QR", Icons.Filled.CameraAlt),
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    NavigationBar(
        containerColor = CafeOscuro,
        contentColor = Blanco
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = {
                    navController.navigate(item.ruta) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icono, contentDescription = item.etiqueta, tint = Blanco) },
                label = { Text(item.etiqueta, color = Blanco) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blanco,
                    selectedTextColor = Blanco,
                    unselectedIconColor = Blanco.copy(alpha = 0.85f),
                    unselectedTextColor = Blanco.copy(alpha = 0.85f),
                    indicatorColor = CafeOscuro
                )
            )
        }
    }
}
