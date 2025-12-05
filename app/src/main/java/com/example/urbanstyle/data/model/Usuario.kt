package com.example.urbanstyle.data.model

import androidx.room.ColumnInfo
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

    val telefono: String,

    val region: String,

    val comuna: String,

    val codigoDescuento: String? = null,


    @ColumnInfo(name = "comentarioLogin")
    val comentarioLogin: String? = null
)
