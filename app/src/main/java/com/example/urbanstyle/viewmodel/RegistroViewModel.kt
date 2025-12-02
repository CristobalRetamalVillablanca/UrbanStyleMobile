package com.example.urbanstyle.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbanstyle.data.database.BaseDeDatos
import com.example.urbanstyle.data.model.Usuario
import com.example.urbanstyle.data.repository.UsuarioRepository
import com.example.urbanstyle.ui.registro.RegistroUiState
import com.example.urbanstyle.utils.regionesDeChile
import com.example.urbanstyle.utils.obtenerComunas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    // 🔹 Lista fija de regiones
    val regiones = regionesDeChile

    // 🔹 Flujo dinámico de comunas según región seleccionada
    private val _comunas = MutableStateFlow<List<String>>(emptyList())
    val comunas: StateFlow<List<String>> = _comunas.asStateFlow()

    init {
        val db = BaseDeDatos.getDatabase(application)
        repository = UsuarioRepository(db.usuarioDao())
    }

    // --- Actualizaciones de campos ---
    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombreCompleto = nuevo, errorNombre = null) }
    }

    fun onCorreoChange(nuevo: String) {
        _uiState.update { it.copy(correo = nuevo, errorCorreo = null) }
    }

    fun onPassChange(nuevo: String) {
        _uiState.update { it.copy(contrasena = nuevo, errorContrasena = null) }
    }

    fun onConfirmPassChange(nuevo: String) {
        _uiState.update { it.copy(confirmarContrasena = nuevo, errorConfirmarContrasena = null) }
    }

    fun onFechaChange(nuevo: String) {
        _uiState.update { it.copy(fechaNacimiento = nuevo) }
    }

    fun onTelefonoChange(nuevo: String) {
        _uiState.update { it.copy(telefono = nuevo) }
    }

    fun onCodigoDescuentoChange(nuevo: String) {
        _uiState.update { it.copy(codigoDescuento = nuevo) }
    }

    fun onRegionChange(nuevo: String) {
        _uiState.update { it.copy(region = nuevo, comuna = "") }
        _comunas.value = obtenerComunas(nuevo)   // ← ACTUALIZA LAS COMUNAS
    }

    fun onComunaChange(nuevo: String) {
        _uiState.update { it.copy(comuna = nuevo) }
    }

    // --- Registrar usuario (nombre corregido para coincidir con la UI) ---
    fun registrarUsuario() {
        val state = _uiState.value
        var hayError = false

        // Validaciones
        if (state.nombreCompleto.length < 4) {
            _uiState.update { it.copy(errorNombre = "Nombre muy corto") }
            hayError = true
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(state.correo).matches()) {
            _uiState.update { it.copy(errorCorreo = "Email inválido") }
            hayError = true
        }
        if (state.contrasena.length < 6) {
            _uiState.update { it.copy(errorContrasena = "Mínimo 6 caracteres") }
            hayError = true
        }
        if (state.contrasena != state.confirmarContrasena) {
            _uiState.update { it.copy(errorConfirmarContrasena = "Las contraseñas no coinciden") }
            hayError = true
        }

        if (hayError) return

        // Guardar en BD
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update { it.copy(isLoading = true) }

                if (repository.existeCorreo(state.correo)) {
                    _uiState.update {
                        it.copy(isLoading = false, errorCorreo = "Este correo ya está registrado")
                    }
                    return@launch
                }

                val nuevoUsuario = Usuario(
                    nombreCompleto = state.nombreCompleto,
                    fechaNacimiento = state.fechaNacimiento,
                    correo = state.correo,
                    contrasena = state.contrasena,
                    telefono = state.telefono,
                    region = state.region,
                    comuna = state.comuna,
                    codigoDescuento = state.codigoDescuento
                )

                repository.registrarUsuario(nuevoUsuario)

                _uiState.update { it.copy(isLoading = false, registroExitoso = true) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, mensajeErrorGeneral = "Error: ${e.message}")
                }
            }
        }
    }
}
