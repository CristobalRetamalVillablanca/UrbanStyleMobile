package com.example.urbanstyle.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreCompleto: String,
    val fechaNacimiento: String,
    val correo: String,
    val contrasena: String,
    val telefono: String, // Campo nuevo añadido
    val region: String,   // Campo nuevo añadido
    val comuna: String,   // Campo nuevo añadido
    val codigoDescuento: String? = null // Opcional
)
