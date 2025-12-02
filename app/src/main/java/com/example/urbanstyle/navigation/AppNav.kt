package com.example.urbanstyle.navigation

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.setValue
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
import com.example.urbanstyle.ui.cart.CartScreen
import com.example.urbanstyle.ui.cart.CartViewModel

import com.example.urbanstyle.ui.NosotrosScreen
import com.example.urbanstyle.utils.CameraPermissionHelper
import com.example.urbanstyle.data.repository.BlogRepository
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.urbanstyle.data.database.BaseDeDatos
import com.example.urbanstyle.data.repository.ProductoRepository
import com.example.urbanstyle.navigation.Rutas
import com.example.urbanstyle.ui.QrScannerScreen
import com.example.urbanstyle.ui.post.PostScreen
import com.example.urbanstyle.ui.registro.RegistroScreen
import com.example.urbanstyle.ui.theme.ApiRestTheme
import com.example.urbanstyle.viewmodel.PostViewModel
import com.example.urbanstyle.viewmodel.QrViewModel

object Rutas {
    const val LOGIN = "login"

    const val REGISTRO = "registro"
    const val HOME = "home"
    const val PRODUCTOS = "productos"

    const val BLOG = "blog"                // Lista
    const val BLOG_DETALLE = "blog/{id}"   // Detalle

    const val API_EXTERNA ="api"
    const val NOSOTROS = "nosotros"
    const val CARRITO = "carrito"
    const val CAMERA = "Escanear QR"

    const val PRODUCTO_DETALLE = "producto/{codigo}"
    const val ARG_CODIGO = "codigo"
}

@Composable
fun AppNav(navController: NavHostController = rememberNavController(),cartViewModel: CartViewModel = viewModel()) {
    val context = LocalContext.current

    var hasCameraPermission by rememberSaveable {
        mutableStateOf(CameraPermissionHelper.hasCameraPermission(context))
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            val message = if (isGranted) "Permiso de cámara concedido" else "Permiso de cámara denegado"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    )

    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN
    ) {
        // ---------- LOGIN ----------
        composable(Rutas.LOGIN) { LoginScreen(navController = navController) }
        // ---------- REGISTRO ----------
        composable(Rutas.REGISTRO) { RegistroScreen(navController = navController) }

        //------------ApiExterna-------
        composable(Rutas.API_EXTERNA){
            val postViewModel: PostViewModel = viewModel()
            ApiRestTheme {
                PostScreen(viewModel = postViewModel)
            }
        }

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

        // ---------- CAMARA ----------
        composable(Rutas.CAMERA){
            val qrViewModel: QrViewModel = viewModel()
            QrScannerScreen(
                viewModel = qrViewModel,
                hasCameraPermission = hasCameraPermission,
                onRequestPermission = {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
        }
        // ---------- CARRITO ----------
        composable(Rutas.CARRITO) { CartScreen(navController = navController, cartViewModel = cartViewModel) }


    }
}
