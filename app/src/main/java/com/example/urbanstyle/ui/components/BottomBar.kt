package com.example.urbanstyle.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.urbanstyle.navigation.Rutas

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
        NavItem(Rutas.CARRITO, "Carrito", Icons.Filled.ShoppingCart) // placeholder
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = { navController.navigate(item.ruta) },
                icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                label = { Text(item.etiqueta) }
            )
        }
    }
}
