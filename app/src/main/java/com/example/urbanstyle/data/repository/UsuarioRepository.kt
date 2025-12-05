package com.example.urbanstyle.data.repository

import com.example.urbanstyle.data.dao.UsuarioDao
import com.example.urbanstyle.data.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun registrarUsuario(usuario: Usuario) {
        usuarioDao.insertarUsuario(usuario)
    }

    suspend fun login(correo: String, pass: String): Usuario? {
        return usuarioDao.login(correo, pass)
    }

    suspend fun existeCorreo(correo: String): Boolean {
        return usuarioDao.obtenerPorCorreo(correo) != null
    }
    suspend fun guardarComentarioLogin(correo: String, comentario: String) {
        usuarioDao.actualizarComentarioLogin(correo, comentario)
    }

}
