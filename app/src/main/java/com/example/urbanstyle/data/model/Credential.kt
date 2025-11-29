package com.example.urbanstyle.data.model

class Credential(val username:String,val password:String) {

    companion object{
        val Admin= Credential(username="Admin", password = "Hola")
        val Cliente = Credential(username = "cliente@duocuc.cl", password = "123456")
    }

}