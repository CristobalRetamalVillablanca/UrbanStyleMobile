package com.example.urbanstyle.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbanstyle.data.database.BaseDeDatos
import com.example.urbanstyle.data.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        val db = BaseDeDatos.getDatabase(application)
        repository = UsuarioRepository(db.usuarioDao())
    }

    fun onCorreoChange(correo: String) {
        _uiState.update { it.copy(correo = correo, errorLogin = null) }
    }

    fun onPasswordChange(pass: String) {
        _uiState.update { it.copy(contrasena = pass, errorLogin = null) }
    }

    fun onComentarioChange(comentario: String) {
        _uiState.update { it.copy(comentario = comentario) }
    }

    fun iniciarSesion() {
        val correo = _uiState.value.correo.trim()
        val pass = _uiState.value.contrasena

        if (correo.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorLogin = "Ingresa correo y contraseña") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            val usuario = repository.login(correo, pass)

            if (usuario != null) {

                val comentarioActual = _uiState.value.comentario.trim()
                if (comentarioActual.isNotEmpty()) {
                    repository.guardarComentarioLogin(correo, comentarioActual)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginExitoso = true,
                        usuarioNombre = usuario.nombreCompleto
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorLogin = "Credenciales incorrectas"
                    )
                }
            }
        }
    }

    // Función para resetear estado al salir de la pantalla
    fun resetState() {
        _uiState.value = LoginUiState()
    }
}
