package com.example.urbanstyle.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.urbanstyle.R
import com.example.urbanstyle.data.repository.ProductoRepository
import com.example.urbanstyle.ui.components.BottomBar
import com.example.urbanstyle.ui.components.TarjetaProducto
import com.example.urbanstyle.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    navController: NavHostController,
    vm: ProductoViewModel = viewModel()
) {
    val desdeVM by vm.productos.collectAsState()
    val productos = if (desdeVM.isNotEmpty()) desdeVM else ProductoRepository().obtenerTodos()

    val pacifico = FontFamily(Font(R.font.pacifico))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Productos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pacifico   // 👈 Pacifico en el título
                    )
                }
                // Si quieres topbar café con texto blanco, descomenta:
                //, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //    containerColor = Color(0xFF5D4037),
                //    titleContentColor = Color.White
                //)
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { inner ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 170.dp),
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos, key = { it.codigo }) { p ->
                Column(Modifier.fillMaxWidth()) {
                    // Card con imagen, nombre, categoría y precio
                    TarjetaProducto(
                        producto = p,
                        onClick = { navController.navigate("producto/${p.codigo}") },
                        onAgregarCarrito = { /* TODO: placeholder */ },
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}
