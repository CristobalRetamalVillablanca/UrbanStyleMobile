package com.example.urbanstyle.data.repository

import com.example.urbanstyle.data.model.Credential

class AuthRepository(private val validCredential: Credential =Credential.Admin) {
    fun login(username:String,password:String):Boolean{
        return username == validCredential.username && password == validCredential.password
    }
}