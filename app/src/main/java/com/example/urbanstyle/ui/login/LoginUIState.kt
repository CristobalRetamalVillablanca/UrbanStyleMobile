package com.example.urbanstyle.ui.login

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val isLoading: Boolean = false,
    val loginExitoso: Boolean = false,
    val errorLogin: String? = null,
    val usuarioNombre: String? = null
)

