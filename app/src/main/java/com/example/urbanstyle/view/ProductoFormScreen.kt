package com.example.urbanstyle.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.urbanstyle.R
import com.example.urbanstyle.data.model.Producto
import com.example.urbanstyle.viewmodel.ProductoViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    vm: ProductoViewModel = viewModel()
) {
    // Campos del formulario
    var codigo by remember { mutableStateOf(TextFieldValue("")) }
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var categoria by remember { mutableStateOf(TextFieldValue("")) }
    var precioTexto by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }

    // Selector de imagen (drawable) con menú
    data class OpcionImagen(val etiqueta: String, val resId: Int)
    val opcionesImagen = listOf(
        OpcionImagen("Torta Chocolate", R.drawable.torta_chocolate),
        OpcionImagen("Torta Vainilla", R.drawable.torta_vainilla),
        OpcionImagen("Torta Frutas", R.drawable.torta_frutas),
        OpcionImagen("Torta Manjar", R.drawable.torta_manjar),
        OpcionImagen("Torta Naranja", R.drawable.torta_naranja),
        OpcionImagen("Torta Vegana", R.drawable.torta_vegana),
        OpcionImagen("Torta Cumpleaños", R.drawable.torta_cumpleanos),
        OpcionImagen("Torta Boda", R.drawable.torta_boda),
        OpcionImagen("Cheesecake", R.drawable.cheesecake),
        OpcionImagen("Tiramisú", R.drawable.tiramisu),
        OpcionImagen("Mousse Chocolate", R.drawable.mousse_chocolate),
        OpcionImagen("Galletas Avena", R.drawable.galletas_avena),
        OpcionImagen("Empanada Manzana", R.drawable.empanada_manzana),
        OpcionImagen("Pan Sin Gluten", R.drawable.pan_sin_gluten),
        OpcionImagen("Brownie", R.drawable.brownie),
    )
    var imagenSeleccionada by remember { mutableStateOf(opcionesImagen.first()) }
    var menuImagenExpandido by remember { mutableStateOf(false) }

    // Observa lista guardada
    val productosGuardados by vm.productos.collectAsState()

    // Validaciones
    val precioEsEntero = precioTexto.text.toIntOrNull()
    val hayErroresPrecio = precioTexto.text.isNotBlank() && precioEsEntero == null
    val formularioValido =
        codigo.text.isNotBlank() &&
                nombre.text.isNotBlank() &&
                categoria.text.isNotBlank() &&
                !hayErroresPrecio &&
                descripcion.text.isNotBlank()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Nuevo Producto") })
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    "Pastelería Mil Sabores",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Vista previa de imagen seleccionada
            Image(
                painter = painterResource(id = imagenSeleccionada.resId),
                contentDescription = imagenSeleccionada.etiqueta,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Código
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código del producto (p.ej. TC001)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Categoría
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría (Tortas, Postres, Panadería...)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Precio (entero CLP)
            OutlinedTextField(
                value = precioTexto,
                onValueChange = { precioTexto = it },
                label = { Text("Precio (CLP)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = hayErroresPrecio,
                supportingText = {
                    if (hayErroresPrecio) Text("Debe ser un número entero (sin puntos).")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            // Selector de imagen (ExposedDropdownMenu)
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = menuImagenExpandido,
                onExpandedChange = { menuImagenExpandido = !menuImagenExpandido },
            ) {
                OutlinedTextField(
                    value = imagenSeleccionada.etiqueta,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Imagen (drawable)") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuImagenExpandido) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuImagenExpandido,
                    onDismissRequest = { menuImagenExpandido = false }
                ) {
                    opcionesImagen.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.etiqueta) },
                            onClick = {
                                imagenSeleccionada = opcion
                                menuImagenExpandido = false
                            }
                        )
                    }
                }
            }

            // Descripción
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(16.dp))

            // Botón guardar
            Button(
                onClick = {
                    val producto = Producto(
                        codigo = codigo.text.trim(),
                        nombre = nombre.text.trim(),
                        categoria = categoria.text.trim(),
                        precio = precioEsEntero ?: 0,
                        imagenRes = imagenSeleccionada.resId,
                        descripcion = descripcion.text.trim()
                    )
                    vm.guardarProducto(producto)

                    // Limpia formulario
                    codigo = TextFieldValue("")
                    nombre = TextFieldValue("")
                    categoria = TextFieldValue("")
                    precioTexto = TextFieldValue("")
                    descripcion = TextFieldValue("")
                },
                enabled = formularioValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar producto")
            }

            Spacer(Modifier.height(16.dp))

            // Lista de productos guardados (preview simple)
            Text("Productos guardados:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productosGuardados) { p ->
                    ElevatedCard {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = p.imagenRes),
                                contentDescription = p.nombre,
                                modifier = Modifier
                                    .size(56.dp)
                                    .padding(end = 12.dp)
                            )
                            Column(Modifier.weight(1f)) {
                                Text("${p.codigo} — ${p.nombre}", style = MaterialTheme.typography.titleSmall)
                                Text(p.categoria, style = MaterialTheme.typography.labelMedium)
                                Text("CLP ${p.precio}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductoFormScreen() {
    ProductoFormScreen(navController = rememberNavController())
}
