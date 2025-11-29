package com.example.urbanstyle.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.DrawableRes

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey
    val codigo: String,
    val nombre: String,
    val categoria: String,
    val precio: Int,
    @DrawableRes val imagenRes: Int,
    val descripcion: String
)
