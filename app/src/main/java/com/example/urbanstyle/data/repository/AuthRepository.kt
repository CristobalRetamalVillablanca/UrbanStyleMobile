package com.example.urbanstyle.data.repository

import com.example.urbanstyle.data.model.Credential

class AuthRepository(private val validCredential:List<Credential> = listOf(Credential.Admin, Credential.Cliente)) {
    fun login(nombre: String, clave: String): Credential? {
        return validCredential.find {
            it.username == nombre && it.password == clave
        }
    }
}