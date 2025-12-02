package com.example.urbanstyle.ui.registro

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.urbanstyle.viewmodel.RegistroViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    // El viewModel() usará el factory por defecto para AndroidViewModel (inyecta Application)
    registroViewModel: RegistroViewModel = viewModel()
) {
    val uiState by registroViewModel.uiState.collectAsState()

    // Navegación al completar registro
    LaunchedEffect(uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Formulario de Registro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Nombre
            OutlinedTextField(
                value = uiState.nombreCompleto,
                onValueChange = { registroViewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.errorNombre != null,
                supportingText = { if (uiState.errorNombre != null) Text(uiState.errorNombre!!) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Fecha
            DatePickerField(
                fechaSeleccionada = uiState.fechaNacimiento,
                onFechaSeleccionada = { registroViewModel.onFechaChange(it) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Correo
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { registroViewModel.onCorreoChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = uiState.errorCorreo != null,
                supportingText = { if (uiState.errorCorreo != null) Text(uiState.errorCorreo!!) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Contraseña
            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = { registroViewModel.onPassChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = uiState.errorContrasena != null,
                supportingText = { if (uiState.errorContrasena != null) Text(uiState.errorContrasena!!) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Confirmar Contraseña
            OutlinedTextField(
                value = uiState.confirmarContrasena,
                onValueChange = { registroViewModel.onConfirmPassChange(it) },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = uiState.errorConfirmarContrasena != null,
                supportingText = { if (uiState.errorConfirmarContrasena != null) Text(uiState.errorConfirmarContrasena!!) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Teléfono
            OutlinedTextField(
                value = uiState.telefono,
                onValueChange = { registroViewModel.onTelefonoChange(it) },
                label = { Text("Teléfono (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Dropdowns
            RegionComunaDropDowns(viewModel = registroViewModel)
            Spacer(modifier = Modifier.height(8.dp))

            // Código Descuento
            OutlinedTextField(
                value = uiState.codigoDescuento,
                onValueChange = { registroViewModel.onCodigoDescuentoChange(it) },
                label = { Text("Código de Descuento (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botón Registrar
            Button(
                onClick = { registroViewModel.registrarUsuario() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Registrarse")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DatePickerField(
    fechaSeleccionada: String,
    onFechaSeleccionada: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onFechaSeleccionada("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Logica para abrir el picker al hacer click en el TextField o Icono
    Box {
        OutlinedTextField(
            value = fechaSeleccionada,
            onValueChange = {},
            label = { Text("Fecha de Nacimiento") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(Icons.Default.DateRange, "Seleccionar Fecha")
            }
        )
        // Overlay transparente clickeable
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { datePickerDialog.show() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionComunaDropDowns(viewModel: RegistroViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val comunas by viewModel.comunas.collectAsState()
    // Nota: viewModel.regiones es una lista fija, no un Flow, así que se accede directo

    var regionExpanded by remember { mutableStateOf(false) }
    var comunaExpanded by remember { mutableStateOf(false) }

    // Dropdown Región
    ExposedDropdownMenuBox(
        expanded = regionExpanded,
        onExpandedChange = { regionExpanded = !regionExpanded }
    ) {
        OutlinedTextField(
            value = uiState.region,
            onValueChange = {},
            readOnly = true,
            label = { Text("Región") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = regionExpanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = regionExpanded,
            onDismissRequest = { regionExpanded = false }
        ) {
            viewModel.regiones.forEach { regionSeleccionada ->
                DropdownMenuItem(
                    text = { Text(regionSeleccionada) },
                    onClick = {
                        viewModel.onRegionChange(regionSeleccionada)
                        regionExpanded = false
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Dropdown Comuna
    ExposedDropdownMenuBox(
        expanded = comunaExpanded,
        onExpandedChange = { if (comunas.isNotEmpty()) comunaExpanded = !comunaExpanded }
    ) {
        OutlinedTextField(
            value = uiState.comuna,
            onValueChange = {},
            readOnly = true,
            label = { Text("Comuna") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = comunaExpanded) },
            enabled = comunas.isNotEmpty(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = comunaExpanded,
            onDismissRequest = { comunaExpanded = false }
        ) {
            comunas.forEach { comunaSeleccionada ->
                DropdownMenuItem(
                    text = { Text(comunaSeleccionada) },
                    onClick = {
                        viewModel.onComunaChange(comunaSeleccionada)
                        comunaExpanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen(navController = rememberNavController())
}