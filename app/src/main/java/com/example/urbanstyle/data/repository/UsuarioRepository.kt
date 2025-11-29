package com.example.urbanstyle.data.repository

import com.example.urbanstyle.data.dao.UsuarioDao
import com.example.urbanstyle.data.model.Usuario



class UsuarioRepository(val usuarioDao: UsuarioDao) {
    suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertarUsuario(usuario)
    }

    suspend fun buscarUsuario(correo: String, contrasena: String): Usuario
    ? {
        return usuarioDao.findUserByEmailAndPassword(correo, contrasena)
    }
}
